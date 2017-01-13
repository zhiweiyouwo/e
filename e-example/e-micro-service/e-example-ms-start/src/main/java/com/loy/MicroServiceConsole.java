/*
 * Copyright   Loy Fu. 付厚俊
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.loy;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.h2.tools.Server;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import de.flapdoodle.embed.process.distribution.Platform;
import de.flapdoodle.embed.process.io.NullProcessor;
import de.flapdoodle.embed.process.runtime.Processes;

/**
 * 
 * @author Loy Fu qq群 540553957 http://www.17jee.com
 * @since 1.8
 * @version 3.0.0
 * 
 */
public class MicroServiceConsole {

    static final Set<Long> pids = new HashSet<Long>();
    static Platform platform = Platform.Windows;
    public static final Object lock = new Object();

    @SuppressWarnings("rawtypes")
    static void init() {

        ClassPathResource classPathResource = new ClassPathResource("application.yml");
        Yaml yaml = new Yaml();
        Map result = null;
        try {
            result = (Map) yaml.load(classPathResource.getInputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String platformStr = result.get("platform").toString();
        String version = result.get("version").toString();
        String jvmOption = result.get("jvmOption").toString();
        @SuppressWarnings("unchecked")
        List<String> projects = (List<String>) result.get("projects");
        platform = Platform.valueOf(platformStr);

        final Runtime runtime = Runtime.getRuntime();
        File pidsForder = new File("./pids");
        if (!pidsForder.exists()) {
            pidsForder.mkdir();
        } else {
            File[] files = pidsForder.listFiles();
            if (files != null) {
                for (File f : files) {
                    f.deleteOnExit();
                    String pidStr = f.getName();
                    try {
                        Long pid = new Long(pidStr);
                        if (Processes.isProcessRunning(platform, pid)) {
                            if (Platform.Windows == platform) {
                                Processes.tryKillProcess(null, platform, new NullProcessor(), pid);
                            } else {
                                Processes.killProcess(null, platform, new NullProcessor(), pid);
                            }

                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }

        File currentForder = new File("");
        String root = currentForder.getAbsolutePath();
        root = root.replace(File.separator + "build" + File.separator + "libs", "");
        root = root.replace(File.separator + "e-example-ms-start", "");
        final String rootPath = root;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int size = projects.size();
                int index = 1;
                for (String value : projects) {
                    String path = value;
                    String[] values = value.split("/");
                    value = values[values.length - 1];
                    value = rootPath + "/" + path + "/build/libs/" + value + "-" + version + ".jar";
                    final String command = value;
                    try {
                        Process process = runtime.exec(
                                "java " + jvmOption + " -Dfile.encoding=UTF-8 -jar " + command);
                        Long pid = Processes.processId(process);
                        pids.add(pid);
                        File pidsFile = new File("./pids", pid.toString());
                        pidsFile.createNewFile();
                        new WriteLogThread(process.getInputStream()).start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock) {
                        try {
                            if (index < size) {
                                lock.wait();
                            } else {
                                index++;
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    static void killProcess() {
        for (Long pid : pids) {
            try {
                if (Processes.isProcessRunning(platform, pid)) {
                    File pidFile = new File("./pids/" + pid.toString());
                    pidFile.deleteOnExit();
                    if (Platform.Windows == platform) {
                        Processes.tryKillProcess(null, platform, new NullProcessor(), pid);
                    } else {
                        Processes.killProcess(null, platform, new NullProcessor(), pid);
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                killProcess();
            }
        }));

        String[] arg = { "-tcp", "-tcpAllowOthers" };
        Server server = Server.createTcpServer(arg);
        server.start();

        init();
    }

}

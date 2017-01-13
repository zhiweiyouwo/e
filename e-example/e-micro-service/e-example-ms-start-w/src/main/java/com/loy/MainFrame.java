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

/**
 * 
 * @author Loy Fu qq群 540553957  http://www.17jee.com
 * @since 1.8
 * @version 3.0.0
 * 
 */
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.h2.tools.Server;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import de.flapdoodle.embed.process.distribution.Platform;
import de.flapdoodle.embed.process.io.NullProcessor;
import de.flapdoodle.embed.process.runtime.Processes;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = -7078030311369039390L;
    private JMenu menu;
    private JMenuBar jmenuBar;
    final Map<String, JScrollPane> textSreaMap = new HashMap<String, JScrollPane>();
    Image image;
    final Set<Long> pids = new HashSet<Long>();
    final Map<String, EComposite> composites = new LinkedHashMap<String, EComposite>();
    Platform platform = Platform.Windows;
    JPanel panel = null;
    MainFrame self = null;

    public static final Object lock = new Object();

    public MainFrame() {
        super("E-MICRO-SERVICE-START");
        self = this;
        init();
        this.setSize(500, 400);
        this.setJMenuBar(jmenuBar);
        this.setLocationRelativeTo(null);
        systemTray();
    }

    @SuppressWarnings("rawtypes")
    public void init() {

        String src = "ee.png";
        try {
            ClassPathResource classPathResource = new ClassPathResource(src);
            image = ImageIO.read(classPathResource.getURL());
            this.setIconImage(image);
        } catch (IOException e) {
        }

        menu = new JMenu("LOG CONSOLE");
        this.jmenuBar = new JMenuBar();
        this.jmenuBar.add(menu);

        panel = new JPanel(new BorderLayout());
        this.add(panel);

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
        String rootPath = currentForder.getAbsolutePath();
        rootPath = rootPath.replace(File.separator + "build" + File.separator + "libs", "");
        rootPath = rootPath.replace(File.separator + "e-example-ms-start-w", "");

        for (String value : projects) {
            String path = value;
            String[] values = value.split("/");
            value = values[values.length - 1];
            String appName = value;
            value = rootPath + "/" + path + "/build/libs/" + value + "-" + version + ".jar";

            JMenuItem menuItem = new JMenuItem(appName);
            JTextArea textArea = new JTextArea();
            textArea.setVisible(true);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setAutoscrolls(true);
            JScrollPane scorll = new JScrollPane(textArea);
            this.textSreaMap.put(appName, scorll);
            EComposite ecomposite = new EComposite();
            ecomposite.setCommand(value);
            ecomposite.setTextArea(textArea);
            composites.put(appName, ecomposite);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Collection<JScrollPane> values = textSreaMap.values();
                    for (JScrollPane t : values) {
                        t.setVisible(false);
                    }
                    String actions = e.getActionCommand();
                    JScrollPane textArea = textSreaMap.get(actions);
                    if (textArea != null) {
                        textArea.setVisible(true);
                        panel.removeAll();
                        panel.add(textArea);
                        panel.repaint();
                        panel.validate();
                        self.repaint();
                        self.validate();
                    }
                }
            });
            menu.add(menuItem);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                int size = composites.keySet().size();
                int index = 1;
                for (String appName : composites.keySet()) {
                    EComposite composite = composites.get(appName);
                    try {

                        Process process = runtime
                                .exec("java " + jvmOption + " -Dfile.encoding=UTF-8 -jar "
                                        + composite.getCommand());
                        Long pid = Processes.processId(process);
                        pids.add(pid);
                        File pidsFile = new File("./pids", pid.toString());
                        pidsFile.createNewFile();

                        new WriteLogThread(process.getInputStream(), composite.getTextArea())
                                .start();
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

    private void systemTray() {
        if (SystemTray.isSupported()) {
            ImageIcon icon = new ImageIcon(image);
            PopupMenu popupMenu = new PopupMenu();

            MenuItem itemShow = new MenuItem("Show");
            MenuItem itemExit = new MenuItem("Exit");
            itemExit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    killProcess();
                    System.exit(0);
                }
            });
            itemShow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                }
            });

            popupMenu.add(itemShow);
            popupMenu.add(itemExit);
            TrayIcon trayIcon = new TrayIcon(icon.getImage(), "E-MICRO-SERVICE-START", popupMenu);
            SystemTray sysTray = SystemTray.getSystemTray();
            try {
                sysTray.add(trayIcon);
            } catch (AWTException e1) {
            }
        }
    }

    void killProcess() {
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

        String[] arg = { "-tcp", "-tcpAllowOthers" };
        Server server = Server.createTcpServer(arg);
        server.start();
        new MainFrame().setVisible(true);

    }

}

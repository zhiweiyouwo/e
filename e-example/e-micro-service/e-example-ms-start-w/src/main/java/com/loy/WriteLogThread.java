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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JTextArea;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Loy Fu qq群 540553957  http://www.17jee.com
 * @since 1.8
 * @version 3.0.0
 * 
 */
public class WriteLogThread extends Thread {
    protected final Log logger = LogFactory.getLog(WriteLogThread.class);
    InputStream inputStream;
    JTextArea textArea;
    boolean notify = false;

    public WriteLogThread(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public WriteLogThread(InputStream inputStream, JTextArea textArea) {
        this.inputStream = inputStream;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = null;
        try {
            while ((str = bufferedReader.readLine()) != null) {
                if (!notify && str.indexOf("JVM running for") != -1) {
                    synchronized (MainFrame.lock) {
                        MainFrame.lock.notify();
                        notify = true;
                    }
                }

                if (this.textArea == null) {
                    System.out.println(str);
                    //logger.info(str);
                } else {
                    this.textArea.append(str);

                    this.textArea.append("\r\n");
                    this.textArea.setCaretPosition(this.textArea.getText().length());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

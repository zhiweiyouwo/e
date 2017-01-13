/*
 * Copyright   Loy Fu.付厚俊
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

import java.sql.SQLException;

import org.h2.tools.Server;

/**
 * 
 * @author Loy Fu qq群 540553957  http://www.17jee.com
 * @since 1.8
 * @version 3.0.0
 * 
 */
public class MainDb {
    public static void main(String[] args) throws SQLException {

        String[] arg = { "-tcp", "-tcpAllowOthers" };
        Server server = Server.createTcpServer(arg);
        server.start();

		System.out.println("JVM running for");
    }

}

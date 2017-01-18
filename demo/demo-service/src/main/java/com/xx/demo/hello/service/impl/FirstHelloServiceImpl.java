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
package com.xx.demo.hello.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.util.TableToExcelUtil;
import com.xx.demo.hello.domain.FirstHelloQueryParam;
import com.xx.demo.hello.domain.entity.FirstHelloEntity;
import com.xx.demo.hello.repository.FirstHelloRepository;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "**/firstHello", method = { RequestMethod.POST, RequestMethod.GET })
@Transactional
public class FirstHelloServiceImpl{
    protected final Log logger = LogFactory.getLog(FirstHelloServiceImpl.class);

    @Autowired
    FirstHelloRepository firstHelloRepository;

    @ControllerLogExeTime(description = "分页查询hello", log = false)
    @RequestMapping(value = "/page")

   
    public Page<FirstHelloEntity> queryPage(FirstHelloQueryParam firstHelloQueryParam,
            Pageable pageable) {
        Page<FirstHelloEntity> page = firstHelloRepository.findFirstHelloPage(firstHelloQueryParam, pageable);
        return page;
    }

    @ControllerLogExeTime(description = "保存hello")
    @RequestMapping(value = "/save", method = { RequestMethod.POST })

    public void save( FirstHelloEntity firstHelloEntity) {
        firstHelloRepository.save(firstHelloEntity);
    }

    @ControllerLogExeTime(description = "获取hello",log=false)
    @RequestMapping(value = "/get", method = { RequestMethod.POST, RequestMethod.GET})

    public FirstHelloEntity get( String id) {
        FirstHelloEntity firstHelloEntity = firstHelloRepository.get(id);
        return firstHelloEntity;
    }
    
    @ControllerLogExeTime(description = "修改hello")
    @RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.PUT })
   
    public void update( FirstHelloEntity firstHelloEntity) {
      
        firstHelloRepository.save(firstHelloEntity);

    }

    @ControllerLogExeTime(description = "删除hello")
    @RequestMapping(value = "/del", method = { RequestMethod.POST, RequestMethod.DELETE })

    public void del(String id) {

        if (StringUtils.isNotEmpty(id)) {
            String[] idsArr = id.split(",");
            List<String> list = new ArrayList<String>();

            if (idsArr != null) {
                for (String idd : idsArr) {
                    list.add(idd);
                }
                firstHelloRepository.delete(list);
            }
        }
    }

    @RequestMapping(value = "/excel", method = { RequestMethod.POST })
    @ControllerLogExeTime(description = "导出角色", log = false)
    @ApiIgnore
    public void excel(String html, HttpServletResponse response) throws IOException {
        response.setContentType("application/msexcel;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=firstHellos.xls");
        OutputStream out = response.getOutputStream();
        TableToExcelUtil.createExcelFormTable("firstHello", html, 1, out);
        out.flush();
        out.close();
    }
}

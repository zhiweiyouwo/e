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
package com.xx.demo.hello.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.loy.e.core.query.annotation.DynamicQuery;
import com.loy.e.core.repository.GenericRepository;
import com.xx.demo.hello.domain.FirstHelloQueryParam;
import com.xx.demo.hello.domain.entity.FirstHelloEntity;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957  http://www.17jee.com
 * @since 1.8
 * @version 3.0.0
 *
 */
public interface FirstHelloRepository extends GenericRepository<FirstHelloEntity, String> {

    /*
     * 对复杂的查动态查询可以类ibatis这样来写
     *@Query(value=" from  FirstHelloEntity f where 1=1  <notEmpty name='name'> and x.name like '%${name}%'</notEmpty> ")
     */
    @DynamicQuery
    Page<FirstHelloEntity> findFirstHelloPage(FirstHelloQueryParam firstHelloQueryParam, Pageable pageable);
}

package loy.com.upm;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(UpmApplicationMain.class)
//@WebIntegrationTest({ "server.port=19090", "management.port=0" })
public class TestUpmApplication {
/***
    private ETestRestTemplate template = new ETestRestTemplate();


    @Before
    public void setUp() throws Exception {
        template.login();
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testIndexData() {

        String url = template.getUrl()+"/indexData";
        SuccessResponseData result = template.getForObject(url, SuccessResponseData.class);
        assertTrue(result.getSuccess());
        Map indexData = (Map)result.getData();
        assertTrue(indexData != null);
        List list = (List) indexData.get("menuData");
        assertTrue(!list.isEmpty());
    }*/
}

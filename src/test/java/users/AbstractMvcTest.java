package users;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import users.config.CommonConfig;
import users.config.MvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//not using @SpringApplicationConfiguration because IDEA cannot recognize autowired beans
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = { CommonConfig.class, MvcConfig.class, MvcTestConfig.class })
public abstract class AbstractMvcTest {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void initMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}

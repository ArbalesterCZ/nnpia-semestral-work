package st43189;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import st43189.dto.ProductDto;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductRepositoryTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void saveProductTest() throws Exception {

        ProductDto dto = new ProductDto();
        dto.setPrice(BigDecimal.TEN);
        dto.setName("AMD Ryzen 9 5950X");
        dto.setDescription("16-core processor.");
        String data = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Is.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(dto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Is.is(dto.getDescription())));
    }
}

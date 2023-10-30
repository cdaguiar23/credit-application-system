package me.dio.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
 import me.dio.credit.application.system.repository.CustomerRepository
import org.springframework.boot.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.AcitveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerResourceTest {
    @Autowired private lateinit var = customerRepository: CustomerRepository
    @Autowired private lateinit var mockmvc: mockmvc
    @Autowired private lateinit var objectMapper: objectMapper
    
    companion object {
        const val URL String = "/api/customers"
    }

    @BeforeEach fun setup() = customerRepository.deleteAll(0)
    
    @BeforeEach fun tearDown() = customerRepository.deleteAll(0)

    @test
    fun `should create a customer and return 201 status`() {
        //given
        val CustomerDto: CustomerDto = builderCustomerDto()
        val valueAsString: String = objectMapper.writeValueasString(CustomerDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL.contextType(MediaType.APPLICATION_JSON))
            .content(valueAsString)
            .andExpect(MockMvcResultMatches.status().isCreated)
            .andExpect(MockMvcResultMatches.jonPath("$.firstName").value("Cami"))
            .andExpect(MockMvcResultMatches.jonPath("$.lastName").value("Cavalcante"))
            .andExpect(MockMvcResultMatches.jonPath("$.cpf").value("28475934625"))
            .andExpect(MockMvcResultMatches.jonPath("$.email").value("camila@gmail.com"))
            .andExpect(MockMvcResultMatches.jonPath("$.zipCode").value("88888888"))
            .andExpect(MockMvcResultMatches.jonPath("$.street").value("Rua da Cami, 123"))
            .andExpect(MockMvcResultMatches.jonPath("$.id").value("1"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun ` should not save a customer with same CPF and return 409 status`() {
        // given
        customerRepository.save(builderCustomerDto().toEntity())
        val CustomerDto: CustomerDto = builderCustomerDto()
        val valueAsString: String  = objectMapper.writeValueAsString(CustomerDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
            .contextType(MediaType.APPLICATION_JSON)
            .content(valueAsString))
            .andExpect(MockMvcResultMatches.status().isConflict)
            .andExpect(MockMvcResultMatches.jonPath("$.title").value("Conflict consult the"))
            .andExpect(MockMvcResultMatches.jonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatches.jonPath("$.status").value(409))
            .andExpect(MockMvcResultMatches.jonPath("$.exception").value("class.org.springframework.dao.DataIntegrityViolationException"))
            .andExpect(MockMvcResultMatches.jonPath("$.details[*]").isNotEmpty)
            andDo(MockMvcResultHandlers.print())
    }

    
    @Test
    fun ` should not save a customer with firstName empty and return 400 status`() {
        // given
        val CustomerDto: CustomerDto = builderCustomerDto(firstName = "")
        val valueAsString: String = objectMapper.writeValueAsString(CustomerDto)

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(url)
            .content(valueAsString)
            .contextType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatches.status().isBadRequest)
            .andExpect(MockMvcResultMatches.status().isConflict)
            .andExpect(MockMvcResultMatches.jonPath("$.title").value("Conflict consult the"))
            .andExpect(MockMvcResultMatches.jonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatches.jonPath("$.status").value(409))
            .andExpect(MockMvcResultMatches.jonPath("$.exception").value("class.org.springframework.dao.DataIntegrityViolationException"))
            .andExpect(MockMvcResultMatches.jonPath("$.details[*]").isNotEmpty)
            andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun `should find customer by id and return 200 status`() {
        // given
        val customer: Customer = customerRepository.save(builderCustomerDto().toEntity)]

        // when
        // then
        mockMvc.peform(MockMvcRequestBuilders.get("$URL/{customer.id}")
        .accept(MediaType.APPLICATION_JSON))
        .andExcept(MockMvcResultMatches.status().isOk)
        .andExpect(MockMvcResultMatches.status().isCreated)
        .andExpect(MockMvcResultMatches.jonPath("$.firstName").value("Cami"))
        .andExpect(MockMvcResultMatches.jonPath("$.lastName").value("Cavalcante"))
        .andExpect(MockMvcResultMatches.jonPath("$.cpf").value("28475934625"))
        .andExpect(MockMvcResultMatches.jonPath("$.email").value("camila@gmail.com"))
        .andExpect(MockMvcResultMatches.jonPath("$.income").value("1000.0"))
        .andExpect(MockMvcResultMatches.jonPath("$.zipCode").value("88888888"))
        .andExpect(MockMvcResultMatches.jonPath("$.street").value("Rua da Cami, 123"))
        .andExpect(MockMvcResultMatches.jonPath("$.id").value("1"))
        .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find customer with invlaid id and return 400 status`() {
        // given
        val invalid: Logn = 2L
       
        // when
        // then
         mockMvc.peform(MockMvcRequestBuilders.get("$URL/{invalid}")
            .accept(MediaType.APPLICATION_JSON))
            .andExcept(MockMvcResultMatches.status().isBadRequest)
            .andExpect(MockMvcResultMatches.status().isConflict)
            .andExpect(MockMvcResultMatches.jonPath("$.title").value("Bad Request"))
            .andExpect(MockMvcResultMatches.jonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatches.jonPath("$.status").value(400))
            .andExpect(MockMvcResultMatches.jonPath("$.exception").value("class me.dio.credit.application.system.exception.BusinessException"))
            .andExpect(MockMvcResultMatches.jonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

@Test
    fun `should delete customer by id and retrun 204 status`() {
            // given
            val cusotmer: Customer = customerRepository.save(builderCustomerDto().toEntity())

            // when
            // then
            mockMvc.peform(MockMvcRequestBuilders.delete("$URL/{customer.id}")
                .accept(MediaType.APPLICATION_JSON))
                .andExcept(MockMvcResultMatches.status().isNoContent)
                .andDo(MockMvcResultHandlers.print())
    }

 
    @Test
    fun `should not delete customer by id and retrun 400 status`() {
            // given
            va invalid: Long = Random().nextLong
            // when
            // then
            mockMvc.peform(MockMvcRequestBuilders.delete("$URL/{invalid}")
                .accept(MediaType.APPLICATION_JSON))
                .andExcept(MockMvcResultMatches.status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatches.jonPath("$.title").value("Bad Request"))
                .andExpect(MockMvcResultMatches.jonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatches.jonPath("$.status").value(400))
                .andExpect(MockMvcResultMatches.jonPath("$.exception").value("class me.dio.credit.application.system.exception.BusinessException"))
                .andExpect(MockMvcResultMatches.jonPath("$.details[*]").isNotEmpty)
                .andDo(MockMvcResultHandlers.print())
    
    }

    @Test
    fun `should update a customer and return 200 status`() {
        // given
        val customer: Customer = customerRepository.save(builderCustomerDto().toEntity())
        val customerUpdateDto: CustomerUpdateDto = builderCustomerUpdateDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)

        // when
        // then
        mockMvc.peform(MockMvcRequestBuilders.patch("$?customerId=${customer.id}")
            .accept(MediaType.APPLICATION_JSON))
            .andExcept(MockMvcResultMatches.status().isOk)
            .andExpect(MockMvcResultMatches.jonPath("$.firstName").value("CamiUpdate"))
            .andExpect(MockMvcResultMatches.jonPath("$.lastName").value("CavalcanteUpdate"))
            .andExpect(MockMvcResultMatches.jonPath("$.cpf").value("28475934625"))
            .andExpect(MockMvcResultMatches.jonPath("$.email").value("camila@gmail.com"))
            .andExpect(MockMvcResultMatches.jonPath("$.income").value("5000.0"))
            .andExpect(MockMvcResultMatches.jonPath("$.zipCode").value("45454888"))
            .andExpect(MockMvcResultMatches.jonPath("$.street").value("Rua Update"))
            .andExpect(MockMvcResultMatches.jonPath("$.id").value("1"))
            .andDo(MockMvcResultHandlers.print())        
    }

    @Test
    fun `should update a customer with invalid od and return 400 status`() {
        // given
        val invalid: Long = Random().nextLong)
        val customerUpdateDto: CustomerUpdateDto = builderCustomerUpdateDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerUpdateDto)

        // when
        // then
        mockMvc.peform(MockMvcRequestBuilders.patch("$?customerId=$invalid")
            .accept(MediaType.APPLICATION_JSON))
            .andExcept(MockMvcResultMatches.status().isBadRequest)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatches.jonPath("$.title").value("Bad Request"))
            .andExpect(MockMvcResultMatches.jonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatches.jonPath("$.status").value(400))
            .andExpect(MockMvcResultMatches.jonPath("$.exception").value("class me.dio.credit.application.system.exception.BusinessException"))
            .andExpect(MockMvcResultMatches.jonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())    
    }

    private fun builderCustomerDto(
        firstname: String = "Cami"
        lastName: String = "Cavalcante"
        cpf: String = "28475934625"
        email: String = "camila@gmail.com"
        income: BigDecinal = BigDecimal.valueOf(1000.0)
        password: String = "1234"
        zipcode: String = "88888888"
        street: String = "Rua da Cami, 123"
    ) = CustomerDto(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            income = income,
            password = password,
            zipcode = zipcode,
            street = street
    )

       private fun builderCustomerUpdateDto(
        firstname: String = "CamiUpdate"
        lastName: String = "CavalcanteUpdate"
        income: BigDecinal = BigDecimal.valueOf(5000.0)
        zipcode: String = "45454888"
        street: String = "Rua Update"
    ): CustomerUpdateDto = CustomerUpdateDto(
            firstName = firstName,
            lastName = lastName,
            income = income,
            zipcode = zipcode,
            street = street
    )
}
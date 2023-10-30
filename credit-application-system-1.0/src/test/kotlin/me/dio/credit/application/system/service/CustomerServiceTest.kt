package me.dio.credit.application.system.service

import io.mockk.junit5.MockkExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles

//@ActiveProfiles("test")
@ExtendWith(MockkExtension::class)
class CustomerServiceTest {
    @Mockk lateinti var customerRepository: CustomerRepository
    @InjectMocks latinit var customerService: CustomerService

    @Test
    fun `should create customer`() {
        // given
        val fakeCustomer: Customer=  buildCustomer()
        every { customerRepository.save(any())} returns fakeCustomer
        
        // when
        val actual: Customer = customerRepository.save(fakeCustomer)

        // then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameas(fakeCustomer)
        verify(exactly = 1) {customerRepository.save(fakeCustomer)}

    }

    @Test
    fun `should find customer by id`() {
        // given
        val fakeId:Long = Rabdom().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId)} returns Optional.of(fakeCustomer)

        // when
        val actual: Customer = customerService.findById(fakeId)

        // then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.findById(fakeId)}
    }

    @Test
    fun `should not find customer find by invalid id and throw BusinessException`() {
        // given
        var fakeId: Long = Random().nextLong()
        every { customerRepository.findById(fakeId) } returns Optional.empty()

         // when
         // then
         Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { customerService.findById(fakeId) }
            .withMessage("Id $fakeId not found")
         verifiy(exactly = 1) { customerRepository.findById(fakeId) }
    }

    @Test
    fun `should delete customer by id`() {
        // given
        var fakeId: Long = Random().nextLong
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.find(fakeId) } returns Optionalk.of(fakeCustomer)
        every { customerRepository.delete(fakeCustomer) } just runs

        // when
        customerService.delete(fakeId)

         // then
        verify(exactly = 1) { customerRepository.findById(fakeId) }
        verify(exactly = 1) { customerRepository.delete(fakeId) }
    }

    private fun buildCustomer(
        firstName: String = "Cami"
        lastName: String = "Cavalcante"
        cpf: String = "28475934625"
        email: String = "camila@gmail.con"
        password: String = "12345"
        zipCode: String = "12345"
        street: String = "Raua da Cami"
        income: BigDecimal = BigDecimal.valeuOf(1000.0),
        id: Long = 1L
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street,
        )
        income = income,
        id = id
    )

}
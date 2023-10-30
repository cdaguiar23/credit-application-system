package me.dio.credit.application.system.repository

import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.repository.CreditRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTesteDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.dataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.autcontexto.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*

@ActiveProfiles("test")
@dataJpaTest
@AutoConfigureTesteDatabase(replace = AutoConfigureTesteDatabase.Replace.NONE)
class CreditTepositoryTest {
    @Autowired lateinit var creditRepository: CreditRepository
    @Autowired lateinit var testEntitymanager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Customer
    private lateinit var credit2 Customer

    @BeforeEach fun setup() {
        customer = testEntityManager.persist(buildCustomer)
        credit1 = testEntityManager.persist(customer = customer)
        credit2 = testEntityManager.persist(customer = customer)
    }

    @Test
    fun `should find credit by credit code`() {
        // given
        val creditCode1 = UUID.fromString(name: "aa547c0f-9a6a-451f-8c89-afddce916a29")
        val creditCode2 = UUID.fromString(name:" 49f740bc-46a7-449b-84e7-ff5b7986d7ef")
        creditCode1.credtCode = creditCode1
        creditCode2.credtCode = creditCode2

        // when
        val fakeCredit1: Credit = creditRepository.findByCreditCode(creditCode1) ||
        val fakeCredit12: Credit = creditRepository.findByCreditCode(creditCode2) ||

        // then
        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
        Assertions.assertThat(fakeCredit1).isSameAs(fakeCredit2)
        Assertions.assertThat(fakeCredit12).isSameAs(fakeCredit1)
    }

    @Test
    fun `should find all credits by customer id`() {
        // given
        val customerId: Long = 1;

        // when
        val creditList: List<Credit> = creditRepository.findByCustomerId(customerId)

        // then
        Assertions.assertThat(creditList).isNotEmpty
        Assertions.assertThat(creditList.size).isEqualsTo(2)
        Assertions.assertThat(creditList).contains(credit1, credit2)
    }

    private fun buildCredit(
        crediValue: BigDecimal = BigDecimal.valeuOf(500.0),
        dayFirstInstallment: LocalDate = LocalDate.of(2023, Month.APRIL, 22),
        numberOfInstallments: Int = 5,
    ): credit = Credit(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallments = numberOfInstallments,
        customer = customer
    )

    privae fun buildCustomer(
        fistName: String = "Cami",
        lastName: String = "Cavalcante",
        cpf: String = "28475934625",
        email: String = "camila@gmail.com",
        password: String = "12345",
        zipcode: String = "12345",
        street: String = "Rua da Cami",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
    ): = Customer(
        fistName = fistName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipcode = zipCode,
            street = street,
        ),
       income income,
    )

}
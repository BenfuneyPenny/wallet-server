package eu.cryptoeuro.rest

import eu.cryptoeuro.TestUtils
import eu.cryptoeuro.rest.model.Balance
import eu.cryptoeuro.service.BalanceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class BalanceControllerTest extends Specification {

    private BalanceController controller = new BalanceController()
    private sampleBalance = new Balance(1000, "accountId")
    private MockMvc mockMvc


    def setup() {
        mockMvc = TestUtils.getMockMvc(controller)
        controller.balanceService = Mock(BalanceService)
    }

    def "get balance for default account"() {
        given:
        controller.balanceService.getBalance(Optional.empty()) >> sampleBalance
        when:
        MockHttpServletResponse response = mockMvc.perform(get("/v1/balances")).andReturn().response
        then:
        response.status == HttpStatus.OK.value()
        response.contentAsString == '{"amount":1000,"transfer":"accountId"}'
    }

}

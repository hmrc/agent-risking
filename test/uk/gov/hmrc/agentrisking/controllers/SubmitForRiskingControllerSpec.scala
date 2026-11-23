/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.agentrisking.controllers

import play.api.http.Status
import play.api.mvc.Request
import play.api.libs.json.Json
import uk.gov.hmrc.agentrisking.util.RequestSupport.hc
import uk.gov.hmrc.agentrisking.testsupport.ControllerSpec
import uk.gov.hmrc.http.HttpReads.Implicits.given
import uk.gov.hmrc.http.HttpResponse
import uk.gov.hmrc.http.StringContextOps
import play.api.libs.ws.JsonBodyWritables.given

class SubmitForRiskingControllerSpec
extends ControllerSpec:

  "return Ok with body json" in:
    val riskingRequest = RiskingRequest(application = "ABC Accounting LTD", individuals = Seq("Bill Bobson", "Dave Danson"))
    given request: Request[?] = tdAll.backendRequest

    val response =
      httpClient
        .post(url"$baseUrl/agent-risking/submit-for-risking")
        .withBody(Json.toJson(riskingRequest))
        .execute[HttpResponse]
        .futureValue

    response.status shouldBe Status.OK
    Json.parse(response.body).as[RiskingRequest] shouldBe riskingRequest

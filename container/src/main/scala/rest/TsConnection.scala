package rest

case class TsConnection(connectionId: String,
                        connectionType: String,
                        fromCompanyAccountId: String,
                        companyName: String,
                        country: String,
                        acceptingDocumentProfiles: List[String],
                        email: String) {

}

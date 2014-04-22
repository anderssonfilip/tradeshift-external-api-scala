package container

case class TsConnectionDetail(connectionType: String,
                              connectionId: String,
                              //properties,
                              companyName: String,
                              country: String,
                              identifiers: List[String],
                              //addresslines,
                              dispatchChannelId: String,
                              email: String) {
}
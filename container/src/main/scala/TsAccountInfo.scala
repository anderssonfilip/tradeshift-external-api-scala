package container

case class TsAccountInfo(companyName: String,
                         country: String,
                         companyAccountId: String,
                         identifiers: Map[String, String],
                         addresses: List[TsAddress],
                         documentProfiles: List[String],
                         lookingFor: List[String],
                         offering: List[String],
                         isPublicProfile: Boolean,
                         created: java.time.LocalDateTime
                          ) {


}

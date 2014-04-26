package rest

import java.util.Date

case class TsDocument(documentId: String,
                      id: String,
                      uri: String,
                      state: String,
                      lastEdit: Date,
                      receiverCompanyName: String,
                      /*itemInfos: Map[String, String],*/
                      latestDispatch: TsDispatch) {

}

package rest

import java.util.Date

case class TsDispatch(dispatchId: String,
                      objectId: String,
                      created: Date,
                      senderIssuerId: String,
                      dispatchState: String,
                      lastStateChange: Date,
                      receiverConnectionId: String,
                      dispatchChannel: String) {

}

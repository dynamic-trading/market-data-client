    Price Data Request
requestType | [ instrumentId ]|
   (Int8)         (Int32)     |
<-----------LENGTH-------->   |

Request Type
1: Subscribe
0: Unsubscribe

            Price Data Response                       
messageType | instrumentId | [ exchangeId ]|
  (Int8)        (Int32)         (Int32)    |
<------------------LENGTH----------------->|

messageType
87: Price Stream
76: Price Data Response

                                 Price Stream                                                                                                           
messageType | instrumentId | numInGroup  [  mdEntryType  |  price  |  exchangeId  ]>
    (Int8)      (Int32)      (Int16)          (Int8)      (Float32)    (Int32)     >
<------------------------------------LENGTH---------------------------------------->

messageType
87: Price Stream
76: Price Data Response

MDEntryType
48: Bid
49: Offer
50: Trade
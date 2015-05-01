Useful ES commands:

DELETE /guestlist/event/

PUT /guestlist/_mapping/event
{
    "event" : {
        "properties" : {
            "eventDate" : {"type" : "date", "format" : "yyyy-MM-dd'T'HH:mm:ss.SSSZ" }
        }
    }
}

package com.binar.gosky

import com.binar.gosky.data.network.model.tickets.Tickets
import com.binar.gosky.data.network.model.tickets.TicketsItem

object DummyData {
    fun getDummyTickets(): Tickets {
        val dummyList = ArrayList<TicketsItem>()
        repeat(10) {
            val item = TicketsItem(
                from = "JAKARTA",
                to = "MEDAN"
            )
            dummyList.add(item)
        }
        return Tickets(
            data = dummyList
        )
    }
}
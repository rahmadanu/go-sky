package com.binar.gosky.data.local.mapper

import com.binar.gosky.data.local.model.TicketsItemWishlist
import com.binar.gosky.data.network.model.tickets.TicketsItem

fun TicketsItem.toTicketsItemWishlist() = TicketsItemWishlist(
    id = id,
    category = category,
    departureTime = departureTime,
    description = description,
    flightNumber = flightNumber,
    from = from,
    to = to,
    imageId = imageId,
    imageUrl = imageUrl,
    price = price,
    returnTime = returnTime,
    duration = duration
)

fun TicketsItemWishlist.toTicketsItem() = TicketsItem(
    id = id,
    category = category,
    departureTime = departureTime,
    description = description,
    flightNumber = flightNumber,
    from = from,
    to = to,
    imageId = imageId,
    imageUrl = imageUrl,
    price = price,
    returnTime = returnTime,
    duration = duration
)
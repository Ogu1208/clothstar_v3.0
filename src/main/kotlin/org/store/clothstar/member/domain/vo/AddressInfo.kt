package org.store.clothstar.member.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
class AddressInfo(
    val addressBasic: String,
    val addressDetail: String,
    val zipNo: String,
)
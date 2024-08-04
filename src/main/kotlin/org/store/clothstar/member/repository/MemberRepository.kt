package org.store.clothstar.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.store.clothstar.member.domain.Member

interface MemberRepository : JpaRepository<Member, Long> {
//    @Query(
//        value = "select m from member m where m.deletedAt is null order by m.createdAt desc",
//        countQuery = "select count(m) from member m"
//    )
//    fun findAllOffsetPaging(pageable: Pageable): Page<Member>
//
//    @Query(
//        value = "select m from member m where m.deletedAt is null order by m.createdAt desc",
//        countQuery = "select count(m) from member m"
//    )
//    fun findAllSlicePaging(pageable: Pageable): Slice<Member>
}
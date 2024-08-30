package org.store.clothstar.member.authentication.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.store.clothstar.member.application.MemberServiceApplication
import org.store.clothstar.member.dto.request.CreateMemberRequest
import org.store.clothstar.member.service.AccountService
import org.store.clothstar.member.service.MemberService

@Service("normalSignUpService")
class NormalSignUpService(
    private val memberServiceApplication: MemberServiceApplication
): SignUpService<CreateMemberRequest> {
    override fun signUp(request: CreateMemberRequest): Long {
        return memberServiceApplication.signUp(request)
    }
}
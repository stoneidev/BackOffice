package com.stoneistudio.lds.member.steps;

import com.stoneistudio.lds.member.application.usecase.MemberUseCase;
import com.stoneistudio.lds.member.domain.member.entity.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemberSteps {

    @Autowired
    private MemberUseCase memberUseCase;

    private Member member;
    private List<Member> members;
    private Long memberId;

    @Given("{string}이라는 이름과 {string}이라는 이메일을 가진 회원이 있습니다")
    public void a_member_with_name_and_email(String name, String email) {
        member = new Member(name, email);
    }

    @When("회원을 추가합니다")
    public void add_the_member() {
        memberUseCase.addMember(member);
    }

    @Then("회원이 성공적으로 저장되어야 합니다")
    public void the_member_should_be_saved_successfully() {
        Member savedMember = memberUseCase.getMemberById(member.getMemberId());
        assertNotNull(savedMember);
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getEmail(), savedMember.getEmail());
    }

    @Given("시스템에 기존 회원들이 존재합니다")
    public void there_are_existing_members_in_the_system() {
        memberUseCase.addMember(new Member("홍길동", "hong@example.com"));
        memberUseCase.addMember(new Member("김철수", "kim@example.com"));
    }

    @When("모든 회원을 요청합니다")
    public void request_all_members() {
        members = memberUseCase.getAllMembers();
    }

    @Then("모든 회원 목록을 받아야 합니다")
    public void should_receive_a_list_of_all_members() {
        assertNotNull(members);
        assertTrue(members.size() >= 2);
    }

    @Given("ID가 {long}인 회원이 시스템에 존재합니다")
    public void a_member_with_id_exists_in_the_system(Long id) {
        member = new Member("테스트 회원", "test@example.com");
        memberUseCase.addMember(member);
        memberId = member.getMemberId();
    }

    @When("ID가 {long}인 회원을 요청합니다")
    public void request_the_member_with_id(Long id) {
        member = memberUseCase.getMemberById(id);
    }

    @Then("정확한 회원 정보를 받아야 합니다")
    public void should_receive_the_correct_member_details() {
        assertNotNull(member);
        assertEquals(memberId, member.getMemberId());
    }

    @Given("ID가 {long}인 회원이 시스템에 존재하지 않습니다")
    public void a_member_with_id_does_not_exist_in_the_system(Long id) {
        memberId = id;
    }

    @Then("회원을 찾을 수 없다는 응답을 받아야 합니다")
    public void should_receive_no_member() {
        assertNull(member);
    }

    @When("회원의 이름을 {string}로 업데이트합니다")
    public void update_the_member_name_to(String newName) {
        member.setName(newName);
        memberUseCase.updateMember(member);
    }

    @Then("회원 정보가 성공적으로 업데이트되어야 합니다")
    public void the_member_should_be_updated_successfully() {
        Member updatedMember = memberUseCase.getMemberById(memberId);
        assertEquals("김철수", updatedMember.getName());
    }

    @When("ID가 {long}인 회원을 삭제합니다")
    public void delete_the_member_with_id(Long id) {
        memberUseCase.deleteMember(id);
    }

    @Then("회원이 성공적으로 삭제되어야 합니다")
    public void the_member_should_be_deleted_successfully() {
        assertNull(memberUseCase.getMemberById(memberId));
    }
}

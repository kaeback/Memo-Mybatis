package org.example.manager;

import org.example.dao.MemoDao;
import org.example.model.Member;
import org.example.model.Memo;

public class MemoManager {

    private MemoDao dao = new MemoDao();

    // 회원가입
    public void saveMember(Member member) {
        dao.saveMember(member);
    }

    // 로그인
    public Member login(Member member) {
        return dao.login(member);
    }

    // 이메일로 회원정보 검색
    public Member findMemberByEmail(String email) {
        return dao.findMemberByEmail(email);
    }

    // 메모 보내기
    public void sendMemo(Memo memo, Member sender, Member receiver) {
        dao.sendMemo(memo, sender.getMember_id(), receiver.getMember_id());
    }

    // 보낸 메모 전체목록(검색)


    // 보낸 메모 읽기

    // 받은 메모 전체목록(검색)

    // 받은 메모 읽기

    // 받은 메모 삭제
}

package org.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.config.MybatisConfig;
import org.example.exception.MemberNotFoundException;
import org.example.model.Member;
import org.example.model.Memo;
import org.example.model.ReceiveMemoRecord;
import org.example.model.SendMemoRecord;

import java.util.List;
import java.util.Map;

public class MemoDao {

    private SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();

    // 회원정보 등록
    public boolean saveMember(Member member) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            Member findMember = mapper.findMemberByEmail(member.getEmail());
            if (findMember != null) {
                mapper.saveMember(member);
                session.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 로그인
    public Member login(Member member) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findMember(member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 이메일로 회원정보 검색
    public Member findMemberByEmail(String email) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findMemberByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 메모 보내기
    public void sendMemo(Memo memo, long sender_id, long receiver_id) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            // 메모 저장
            mapper.saveMemo(memo);

            // 보낸 메모 내역 저장
            SendMemoRecord sendMemoRecord = new SendMemoRecord(memo.getMemo_id(), sender_id, receiver_id);
            mapper.saveSendMemoRecord(sendMemoRecord);

            // 받은 메모 내역 저장
            ReceiveMemoRecord receiveMemoRecord = new ReceiveMemoRecord(memo.getMemo_id(), sender_id, receiver_id);
            mapper.saveReceiveMemoRecord(receiveMemoRecord);

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 보낸 메모 전체목록(검색)
    public List<Map<String, Object>> findSendMemos(String title) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findSendMemos(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 보낸 메모 읽기
    public Map<String, Object> findSendMemoById(long send_id) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findSendMemoById(send_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 받은 메모 전체목록(검색)
    public List<Map<String, Object>> findReceiveMemos(String title) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findReceiveMemos(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 받은 메모 읽기
    public Map<String, Object> findReceiveMemoById(long receive_id) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            return mapper.findReceiveMemoById(receive_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 받은 메모 삭제
    public void removeReceiveMemoRecordById(long receive_id) {
        try (SqlSession session = factory.openSession()) {
            MemoMapper mapper = session.getMapper(MemoMapper.class);
            mapper.removeReceiveMemoRecordById(receive_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.util.ArrayList;
import java.util.List;

public class MemberManager {

    private static List<Member> members = new ArrayList<>();

    public static void addMember(Member member) {
        members.add(member);
    }

    public static void removeMember(Member member){
        members.remove(member);
    }

    public static Member findByUsername(String username) {

        for (Member m : members) {
            if (m.getUsername().equals(username)) {
                return m;
            }
        }

        return null;
    }

    public static List<Member> getAllMembers() {
        return members;
    }
}
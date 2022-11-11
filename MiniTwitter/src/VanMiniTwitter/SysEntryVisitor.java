package VanMiniTwitter;

public interface SysEntryVisitor {
	public double visit(Group group);
	public double visit(User user);
}

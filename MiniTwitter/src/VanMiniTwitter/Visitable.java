package VanMiniTwitter;

public interface Visitable {
	public double accept(SysEntryVisitor visitor);
	public boolean accept(SysEntryVisitorCheck visitor);
	public User accept(SysEntryVisitorFind visitor);
}

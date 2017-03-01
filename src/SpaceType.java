
public class SpaceType {

	private String Name;

	public SpaceType(String name) {
		super();
		Name = name;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		if (name == null){
			throw new IllegalArgumentException("Space type name can't be null");
		}
		Name = name;
	}
}

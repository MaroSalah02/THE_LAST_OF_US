package model.world;

public abstract class Cell {
	private boolean isVisible;

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public Cell() {
	}
}

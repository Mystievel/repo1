package com.tofersapps.ThePurplePot;

// todo: Medium Priority - split background to radio button side and text "Happiness" "Relaxed" to left side avoid annoying accidental bug clicks on the radio buttons bringing up the info page.

public class FindStrainsListItemData extends FindStrainsActivity {
	private String effect;
	private int checkedBtnType;

	public FindStrainsListItemData(String effect) {
		this.effect = effect;
	}

	// getters & setters
	public String getEffect() {
		return effect;
	}

	public int getFilter() {
		return checkedBtnType;
	}

	public void setFilter(int checkedBtn) {
		if (checkedBtn == MIN) {
			this.checkedBtnType = MIN;
		} else if (checkedBtn == MAX) {
			this.checkedBtnType = MAX;
		} else {
			checkedBtnType = IGNORE;
		}
	}
}

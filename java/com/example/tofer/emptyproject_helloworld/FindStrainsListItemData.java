package com.example.tofer.emptyproject_helloworld;

import android.widget.RadioButton;
import android.widget.RadioGroup;

// todo: add "?" graphic with click to see popup info.

public class FindStrainsListItemData extends FindStrainsActivity {
	private String effect;
	private int checkedBtnType;

	public FindStrainsListItemData(String effect){
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

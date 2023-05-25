package it.polito.tdp.borders.model;

import java.util.Objects;

public class Border {
	
	private Country c1;
	private Country c2;
	private int anno;
	public Border(Country c1, Country c2, int anno) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.anno = anno;
	}
	public Country getC1() {
		return c1;
	}
	public void setC1(Country c1) {
		this.c1 = c1;
	}
	public Country getC2() {
		return c2;
	}
	public void setC2(Country c2) {
		this.c2 = c2;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	@Override
	public int hashCode() {
		return Objects.hash(anno, c1, c2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		return anno == other.anno && Objects.equals(c1, other.c1) && Objects.equals(c2, other.c2);
	}
	
	
}

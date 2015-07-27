package me.toolkit.java.entity;

/**
 * @author  wangdi0410 / wangdi0410@gmail.com
 * @Date	 2012-5-11
 */
public enum EnvType {

	TEST("TEST"), DAILY("DAILY"), ONLINE("ONLINE"), PREPARE("PREPARE"), SANDBOX("SANBOX");

	private String envName;

	private EnvType(String envName) {
		this.envName = envName;
	}

	@Override
	public String toString() {
		return this.envName;
	}

}

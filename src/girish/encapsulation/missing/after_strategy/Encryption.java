package girish.encapsulation.missing.after;

public class Encryption {
	private EncryptionAlgorithm algo;

	public Encryption(EncryptionAlgorithm algo) {
		this.algo = algo;
	}

	public void encrypt() {
		algo.encrypt();
	}
}

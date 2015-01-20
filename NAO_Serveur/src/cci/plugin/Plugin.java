package cci.plugin;

public interface Plugin {
	public String lancerModule() throws Exception;
	public String refreshModule();
	public String commande();
}

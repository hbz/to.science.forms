package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FormRegister {
	Map<String, KatalogForm> register = new HashMap();

	public FormRegister() {
		register(new ResearchDataKatalogForm());
	}

	private void register(KatalogForm form) {
		register.put(form.getId(), form);
	}

	public List<String> getIds() {
		return register.keySet().parallelStream().collect(Collectors.toList());
	}

	public KatalogForm get(String id) {
		return register.get(id);
	}
}

package sf.example.spring.async;

import org.springframework.stereotype.Service;

@Service
public class DataWarehouseClient {
	public Boolean slowSave() {
		return true;
	}
}

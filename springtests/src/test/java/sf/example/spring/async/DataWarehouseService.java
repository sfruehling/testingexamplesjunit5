package sf.example.spring.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DataWarehouseService {

	private DataWarehouseClient dataWarehouseClient;

	@Autowired
	public DataWarehouseService(DataWarehouseClient dataWarehouseClient) {
		this.dataWarehouseClient = dataWarehouseClient;
	}

	@Async
	public CompletableFuture<Boolean> callDataWareHouse() {
		return CompletableFuture.completedFuture(dataWarehouseClient.slowSave());
	}
}

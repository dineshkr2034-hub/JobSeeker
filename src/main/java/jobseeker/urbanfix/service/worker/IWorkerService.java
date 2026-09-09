package jobseeker.urbanfix.service.worker;

import jobseeker.urbanfix.dto.WorkerDto;
import jobseeker.urbanfix.model.Category;
import jobseeker.urbanfix.model.Worker;
import jobseeker.urbanfix.request.AddWorkerRequest;
import jobseeker.urbanfix.request.UpdateWorkerRequest;

import java.util.List;

public interface IWorkerService {
    Worker createWorker(AddWorkerRequest worker);
    Worker getWorkerById(Long id);
    List<Worker> getAllWorkers();
    Worker updateWorker(UpdateWorkerRequest request,Long id);
    void deleteWorker(Long id);
    List<Worker> getWorkerByCategory(String  category);
    List<Worker> getWorkerByName(String name);
    List<Worker> getWorkerByNameAndCategory(String name,String category);


    WorkerDto convertToDto(Worker workers);


    List<WorkerDto> getConvertedWorkers(List<Worker> workers);
}

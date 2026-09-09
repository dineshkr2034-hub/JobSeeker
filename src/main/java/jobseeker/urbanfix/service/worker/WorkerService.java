package jobseeker.urbanfix.service.worker;

import jobseeker.urbanfix.dto.WorkerDto;
import jobseeker.urbanfix.exception.ResourceNotFoundException;
import jobseeker.urbanfix.model.Category;
import jobseeker.urbanfix.model.Worker;
import jobseeker.urbanfix.repository.CategoryRepository;
import jobseeker.urbanfix.repository.WorkerRepository;
import jobseeker.urbanfix.request.AddWorkerRequest;
import jobseeker.urbanfix.request.UpdateWorkerRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService implements IWorkerService{

    private final WorkerRepository workerRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Worker createWorker( AddWorkerRequest request) {
        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newcategory= new Category(request.getCategory().getName());
                    return categoryRepository.save(newcategory);
                });
        request.setCategory(category);
        return workerRepository.save(addWorker(request,category));
    }

    private Worker addWorker(AddWorkerRequest request, Category category){
        return new Worker(
                request.getName(),
                request.getWages(),
                request.getContact(),
                category
        );
    }

    @Override
    public Worker getWorkerById(Long id) {
        return workerRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Worker not Found"));
    }

    @Override
    public List<Worker> getAllWorkers() {
        List<Worker> workers= workerRepository.findAll();
        return workers;
    }

    @Override
    public Worker updateWorker(UpdateWorkerRequest request,Long id) {

        return workerRepository.findById(id)
                .map(existingWorker-> updateExistingWorker(existingWorker,request))
                .map(workerRepository::save)
                .orElseThrow(()->new ResourceNotFoundException("Worker not found"));
    }

    private Worker updateExistingWorker(Worker existingWorker, UpdateWorkerRequest request){
        existingWorker.setName(request.getName());
        existingWorker.setWages(request.getWages());
        Category category=categoryRepository.findByName(request.getCategory().getName());
        existingWorker.setCategory(category);
        return existingWorker;
    }

    @Override
    public void deleteWorker(Long id) {
        workerRepository.findById(id).ifPresentOrElse(workerRepository::delete,
                ()-> new ResourceNotFoundException("Worker not found"));
    }

    @Override
    public List<Worker> getWorkerByCategory(String category) {

        return workerRepository.findByCategoryName(category);
    }

    @Override
    public List<Worker> getWorkerByName(String name) {
        return workerRepository.findByName(name);
    }

    @Override
    public List<Worker> getWorkerByNameAndCategory(String name, String category) {
        return workerRepository.findByCategoryAndName(category,name);
    }

    @Override
    public WorkerDto convertToDto(Worker workers) {
        WorkerDto workerDto= modelMapper.map(workers,WorkerDto.class);

        return workerDto;
    }

    @Override
    public List<WorkerDto> getConvertedWorkers(List<Worker> products) {
        return products.stream().map(this::convertToDto).toList();
    }
}

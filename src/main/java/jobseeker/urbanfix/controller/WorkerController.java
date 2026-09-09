package jobseeker.urbanfix.controller;

import jobseeker.urbanfix.dto.WorkerDto;
import jobseeker.urbanfix.exception.AlreadyExistException;
import jobseeker.urbanfix.exception.ResourceNotFoundException;
import jobseeker.urbanfix.model.Worker;
import jobseeker.urbanfix.request.AddWorkerRequest;
import jobseeker.urbanfix.request.UpdateWorkerRequest;
import jobseeker.urbanfix.response.ApiResponse;
import jobseeker.urbanfix.service.worker.IWorkerService;
import jobseeker.urbanfix.service.worker.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/workers")
public class WorkerController {
    private final IWorkerService workerService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createWorker(@RequestBody AddWorkerRequest worker){
        try {
            Worker workers=workerService.createWorker(worker);
            WorkerDto workerDto=workerService.convertToDto(workers);

            return ResponseEntity.ok(new ApiResponse("Successfully created",workerDto));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllWorker(){
        List<Worker> workers=workerService.getAllWorkers();
        List<WorkerDto> workerDto=workerService.getConvertedWorkers(workers);

        return ResponseEntity.ok(new ApiResponse("success",workerDto));
    }
    @GetMapping("/worker/{id}/worker")
    public ResponseEntity<ApiResponse> getWorkerById(@PathVariable Long id){
        try {
            Worker worker= workerService.getWorkerById(id);
            WorkerDto workerDto=workerService.convertToDto(worker);

            return ResponseEntity.ok(new ApiResponse("success",workerDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/worker/{id}/update")
    public ResponseEntity<ApiResponse> updateWorker(@RequestBody UpdateWorkerRequest request,Long id){
        try {
            Worker worker=workerService.updateWorker(request,id);
            return ResponseEntity.ok(new ApiResponse("Updated Success",worker));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/worker/{id}/delete")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Long id){
        try {
            workerService.deleteWorker(id);
            return ResponseEntity.ok(new ApiResponse("deleted success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/worker/{category}/worker-category")
    public ResponseEntity<ApiResponse> getWorkerByCategory(@PathVariable String category){
        try {
            List<Worker> worker= workerService.getWorkerByCategory(category);
            List<WorkerDto> workerDto=workerService.getConvertedWorkers(worker);

            return ResponseEntity.ok(new ApiResponse("success",workerDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/worker/{category}/worker-name")
    public ResponseEntity<ApiResponse> getWorkerByName(@PathVariable String name){
        try {
            List<Worker> worker= workerService.getWorkerByName(name);
            List<WorkerDto> workerDto=workerService.getConvertedWorkers(worker);

            return ResponseEntity.ok(new ApiResponse("success",workerDto));
        } catch (ResourceNotFoundException e) {

            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/worker/{category}/worker-name-and-category")
    public ResponseEntity<ApiResponse> getWorkerByNameAndCategory(@RequestParam String name, @RequestParam String category){
        try {
            List<Worker> worker= workerService.getWorkerByNameAndCategory(name,category);
            List<WorkerDto> workerDto=workerService.getConvertedWorkers(worker);
            return ResponseEntity.ok(new ApiResponse("success",workerDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}

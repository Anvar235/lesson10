package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Subject;
import uz.pdp.appjparelationships.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    //CREATE
    @RequestMapping(method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject) {
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName)
            return "This subject already exist";
        subjectRepository.save(subject);
        return "Subject added";
    }

    //READ
//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Subject> getSubjects() {
        List<Subject> subjectList = subjectRepository.findAll();
        return subjectList;
    }

    @GetMapping("findBySubjectId/{subjectId}")
    public Subject findBySubjectId(@PathVariable Integer subjectId){
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        return optionalSubject.orElseGet(Subject::new);
    }

    @PutMapping("/editSubject/{subjectId}")
    public String editSubject(@PathVariable Integer subjectId, @RequestBody Subject subject){
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        if (!optionalSubject.isPresent())
            return "Subject not found";
        Subject subject1 = optionalSubject.get();
        subject1.setName(subject.getName());
        subjectRepository.save(subject1);
        return "Subject edited";
    }

    @DeleteMapping("/{subjectId}")
    public String deleteSubject(@PathVariable Integer subjectId){
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        if (!optionalSubject.isPresent())
            return "Subject not found";
        subjectRepository.deleteById(subjectId);
        return "Subject deleted";
    }
}

package by.tms.school.controller;

import by.tms.school.model.Category;
import by.tms.school.model.LessonExamination;
import by.tms.school.service.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/editor")
@Validated
public class EditorController {

    private final EditorService editorService;

    @Autowired
    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @PutMapping(path = "/updCrsName/{courseId}")
    public ResponseEntity<String> updCourse(@PathVariable("courseId") @Min(1) long courseId,
                                         @RequestParam String name){
        return new ResponseEntity(editorService.editCourseName(courseId, name), HttpStatus.OK);
    }

    @PutMapping(path = "/updLsn/{lessonId}")
    public ResponseEntity<String> updLesson(@PathVariable("lessonId") @Min(1) long lessonId,
                                            @RequestParam String name,
                                            @RequestBody LessonExamination lsExam, @RequestBody File content ){
        return new ResponseEntity(editorService.
                editLesson(lessonId,name,lsExam,content), HttpStatus.OK);
    }

    @PutMapping(path = "/updLsEx/{lsExamId}")
    public ResponseEntity<String> updLsExam(@PathVariable("lsExamId") @Min(1) long lsExamId,
                                            @RequestParam String questions,
                                            @RequestParam int answer){
        return new ResponseEntity(editorService.editLessonExamination(lsExamId, questions, answer), HttpStatus.OK);
    }

}

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
import java.io.File;

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
    public ResponseEntity<String> updCourseName(@PathVariable("courseId") @Min(1) long courseId,
                                         @RequestParam String name){
        return new ResponseEntity(editorService.editCourseName(courseId, name), HttpStatus.OK);
    }

    @PutMapping(path = "/updCrsCtg/{courseId}")
    public ResponseEntity<String> updCourseCategories(@PathVariable("courseId") @Min(1) long courseId,
                                            @RequestBody Category category){
        return new ResponseEntity(editorService.addNewCourseCategory(courseId, category), HttpStatus.OK);
    }

    @PutMapping(path = "/updLsnName/{lessonId}")
    public ResponseEntity<String> updLessonName(@PathVariable("lessonId") @Min(1) long lessonId,
                                            @RequestParam String name){
        return new ResponseEntity(editorService.
                editLessonName(lessonId,name), HttpStatus.OK);
    }

    @PutMapping(path = "/updLsnContent/{lessonId}")
    public ResponseEntity<String> updLessonContent(@PathVariable("lessonId") @Min(1) long lessonId,
                                                @RequestBody File content){
        return new ResponseEntity(editorService.
                editLessonContent(lessonId,content), HttpStatus.OK);
    }

    @PutMapping(path = "/updLsnLsExam/{lessonId}")
    public ResponseEntity<String> updLessonLsExam(@PathVariable("lessonId") @Min(1) long lessonId,
                                                @RequestBody LessonExamination lessonExamination){
        return new ResponseEntity(editorService.editLessonLsExam(lessonId, lessonExamination), HttpStatus.OK);
    }



}

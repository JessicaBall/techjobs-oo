package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job job = jobData.findById(id);
        model.addAttribute("name", job.getName());
        model.addAttribute("employer", job.getEmployer());
        model.addAttribute("location", job.getLocation());
        model.addAttribute("positionType", job.getPositionType());
        model.addAttribute("coreCompetency", job.getCoreCompetency());

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
//
        if (errors.hasErrors()) {
           model.addAttribute(new JobForm());
            return "new-job";
       }


        Employer employer = new Employer(jobForm.getEmployer());
        Location location = new Location(jobForm.getLocation());
        PositionType positionType = new PositionType((jobForm.getPositionType()));
        CoreCompetency coreCompetency = new CoreCompetency((jobForm.getCoreCompetency()));

        Job newJob = new Job(jobForm.getName(),employer,location,positionType,coreCompetency);

        int id = newJob.getId();

        jobData.add(newJob);

        return "redirect:?id="+id;


    }
}

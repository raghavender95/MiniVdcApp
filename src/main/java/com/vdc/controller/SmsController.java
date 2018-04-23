package com.vdc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vdc.exception.SmsNotFound;
import com.vdc.model.sms;
import com.vdc.queue.JMSConsumer;
import com.vdc.queue.JMSProducer;
import com.vdc.service.SmsService;

@Controller
@RequestMapping(value="/sms")
public class SmsController {
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private JMSProducer jmsProducer;
	

	@Autowired
	private JMSConsumer jmsConsumer;
	


	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newSmsPage() {
		ModelAndView mav = new ModelAndView("sms-new", "sms", new sms());
		return mav;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewSms(@ModelAttribute @Valid sms sms,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("sms-new");
		
		ModelAndView mav = new ModelAndView();
		String message = "New sms "+sms.getContent()+" was successfully Sent.";
		jmsProducer.sendSmsData(sms.getContent());
		smsService.create(sms);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView smsListPage() {
		ModelAndView mav = new ModelAndView("sms-list");
		List<sms> smsList = smsService.findAll();
		mav.addObject("smsList", smsList);
		return mav;
	}
	
	/*@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editSmsPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("sms-edit");
		sms sms = smsService.findById(id);
		mav.addObject("sms", sms);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editSms(@ModelAttribute @Valid sms sms,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws SmsNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("sms-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "SMS was successfully updated.";

		smsService.update(sms);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}*/
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteSms(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws SmsNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");	
		sms sms = smsService.delete(id);
		
		String message = "The sms "+sms.getContent()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
}

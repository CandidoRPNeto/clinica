package br.com.med.clinica.agendamento.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.med.clinica.agendamento.model.Convenio;
import br.com.med.clinica.agendamento.model.Paciente;
import br.com.med.clinica.agendamento.repository.ConvenioRepository;
import br.com.med.clinica.agendamento.repository.PacienteRepository;

@Controller
public class PacienteController {
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private ConvenioRepository convenioRepository;

	@GetMapping("/paciente")
	public String listConvenio(Model model) {
		List<Paciente> pacientes =  pacienteRepository.findAll();
		model.addAttribute("pacientes",pacientes);
		return "/agendamento/paciente";
	}
	
	@GetMapping("/paciente/form")
	public String form(Model model,@Param(value = "id") Long id) {
		Paciente paciente = new Paciente();
		List<Convenio> convenios = convenioRepository.findAll();
		if(id != null) {
			Optional<Paciente> op = pacienteRepository.findById(id);
			if(op.isPresent()) {
				paciente = op.get();
			}
		}
		model.addAttribute("paciente",paciente);
		model.addAttribute("convenios",convenios);
		
		return "/agendamento/pacienteform";
	}
	
	@PostMapping("/paciente/salvar")
	public String salvar(Paciente paciente) {
		if(paciente.getConvenio_id() != 0) {
			Optional<Convenio> convenio = convenioRepository.findById(paciente.getConvenio_id());
			if(convenio.isPresent()) {
				paciente.setConvenio(convenio.get());
			}
			pacienteRepository.save(paciente);
		}
		return "redirect:/paciente";
	}
	

	@GetMapping("/paciente/delete")
	public String delete(Long id) {
		pacienteRepository.deleteById(id);
		return "redirect:/paciente";
	}
}

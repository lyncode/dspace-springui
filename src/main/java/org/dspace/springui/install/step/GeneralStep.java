package org.dspace.springui.install.step;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.dspace.springui.install.model.GeneralInformation;
import org.dspace.springui.install.model.Language;
import org.springframework.ui.ModelMap;

public class GeneralStep extends AbstractStep {

	@Override
	public GeneralInformation validate(HttpServletRequest request) throws InstallException {
		String name = request.getParameter("name");
		String defaulLang = request.getParameter("default-language");
		String[] availableLanguages = request.getParameterValues("available-languages");
		if (availableLanguages == null) availableLanguages = new String[0];
		String url = request.getParameter("url");
		
		request.setAttribute("availableLanguages", Arrays.asList(availableLanguages));
		
		GeneralInformation general = new GeneralInformation();
		general.setName(name);
		general.setDefaultLanguage(Language.fromCode(defaulLang));
		for (String lang : availableLanguages)
			general.getAvailableLanguages().add(Language.fromCode(lang));
		if (!general.getAvailableLanguages().contains(general.getDefaultLanguage()))
			general.getAvailableLanguages().add(general.getDefaultLanguage());
		
		try {
			URL turl = new URL(url);
			general.setUrl(turl);
		} catch (MalformedURLException e) {
			throw new InstallException("Malformed URL",e);
		}
		return general;
	}

	@Override
	public void install(Object values) throws InstallException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getView() {
		return "general";
	}

	@Override
	public void prepare(ModelMap model) {
		model.addAttribute("possibleLanguages", Language.values());
	}


}

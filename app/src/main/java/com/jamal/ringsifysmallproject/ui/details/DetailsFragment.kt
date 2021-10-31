package com.jamal.ringsifysmallproject.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.jamal.ringsifysmallproject.GlideImage
import com.jamal.ringsifysmallproject.R
import com.jamal.ringsifysmallproject.databinding.FragmentDetailsCharacterBinding
import kotlinx.coroutines.launch

class DetailsFragment : Fragment(R.layout.fragment_details_character) {

    private var _binding: FragmentDetailsCharacterBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val character = args.character

            detailsName.text = character.name
            if (character.imageUrl != "") {
                GlideImage.imageHolder(requireContext(), character.imageUrl!!, characterImage)
            } else {
                GlideImage.imageHolder(requireContext(), R.drawable.no_image, characterImage)
            }
            characterDescription.text = character.description
            characterRace.text = character.race
            characterBirth.text = character.birth
            characterDeath.text = character.death
            characterRealm.text = character.realm
            characterCulture.text = character.culture

            var uri: Uri? = null
            uri = if (uri != null) {
                Uri.parse(character.fandomUrl)
            } else {
                Uri.parse("https://lotr.fandom.com/")
            }
            val intent = Intent(Intent.ACTION_VIEW, uri)
            fandomLinkButton.setOnClickListener {
                context?.startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
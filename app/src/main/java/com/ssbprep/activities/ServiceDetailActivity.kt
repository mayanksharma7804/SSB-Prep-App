package com.ssbprep.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssbprep.adapters.KnowledgeCategory
import com.ssbprep.adapters.KnowledgeExpandableAdapter
import com.ssbprep.adapters.KnowledgeSubItem
import com.ssbprep.databinding.ActivityServiceDetailBinding

class ServiceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceType = intent.getStringExtra("SERVICE_TYPE") ?: "Army"
        setupUI(serviceType)

        binding.backButton.setOnClickListener { finish() }

        val categories = getCategoriesForService(serviceType)
        binding.detailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.detailRecyclerView.adapter = KnowledgeExpandableAdapter(categories)
    }

    private fun setupUI(service: String) {
        binding.titleText.text = when (service) {
            "Army" -> "Indian Army"
            "AirForce" -> "Indian Air Force"
            "Navy" -> "Indian Navy"
            "Missiles" -> "Missile Systems"
            "Awards" -> "Honours & Awards"
            "Medical" -> "Professional Medical Knowledge"
            "General" -> "General Knowledge & Science"
            "SWOT" -> "Interview Preparation"
            "INTERVIEW_GUIDE" -> "Complete Interview Guide"
            else -> "Information"
        }
    }

    private fun getCategoriesForService(service: String): List<KnowledgeCategory> {
        return when (service) {
            "Army" -> listOf(
                KnowledgeCategory(
                    "1. Mission and Identity",
                    "Core values and primary objectives.",
                    subItems = listOf(
                        KnowledgeSubItem("Motto", "Seva Paramo Dharma (Service Before Self)."),
                        KnowledgeSubItem("Mission", "To ensure national security and unity, defending from external threats and internal subversion."),
                        KnowledgeSubItem("Doctrine", "Emphasis on deterrence, punitive response, and cold start capabilities.")
                    )
                ),
                KnowledgeCategory(
                    "2. History and Conflicts",
                    "The evolution and combat history of the Indian Army.",
                    subItems = listOf(
                        KnowledgeSubItem("Background & World Wars", "Origin from Presidency Armies; major role in WWI and WWII (North Africa, Burma)."),
                        KnowledgeSubItem("Independence (1947-50)", "Division of Army; 1947 Kashmir War; Annexation of Hyderabad (1948)."),
                        KnowledgeSubItem("Operations (1950-67)", "Korean War assistance; Annexation of Goa (1961); 1962 Sino-Indian War; 1965 Indo-Pak War; 1967 Chola incident."),
                        KnowledgeSubItem("Major Wars", "1971 Bangladesh Liberation War (largest surrender since WWII); 1999 Kargil War (Op Vijay)."),
                        KnowledgeSubItem("Modern Conflicts", "Siachen (Op Meghdoot); 2016 Surgical Strikes; 2019 Balakot fallout; Doklam standoff; UN Peacekeeping.")
                    )
                ),
                KnowledgeCategory(
                    "3. Organisation and Structure",
                    "Commands, Combat Arms, and Services.",
                    subItems = listOf(
                        KnowledgeSubItem("Combat Arms", "Infantry, Armoured Corps, Mechanised Infantry."),
                        KnowledgeSubItem("Support Arms", "Artillery, Engineers, Signals, Army Aviation, Air Defence (AAD)."),
                        KnowledgeSubItem("Services", "ASC, AMC, AOC, EME, AEC, RVC, MNS, ADC."),
                        KnowledgeSubItem("Intelligence", "Military Intelligence (MI) and the new STEAG unit.")
                    ),
                    selectableItems = mapOf(
                        "Western Command" to "Chandimandir",
                        "Eastern Command" to "Kolkata",
                        "Northern Command" to "Udhampur",
                        "Southern Command" to "Pune",
                        "Central Command" to "Lucknow",
                        "South Western Command" to "Jaipur",
                        "Training Command (ARTRAC)" to "Shimla"
                    )
                ),
                KnowledgeCategory(
                    "4. Personnel and Ranks",
                    "Hierarchy and selection routes.",
                    rankGroups = mapOf(
                        "Officers" to listOf("Lieutenant", "Captain", "Major", "Lt Colonel", "Colonel", "Brigadier", "Major General", "Lt General", "General", "Field Marshal"),
                        "JCOs/NCOs/ORs" to listOf("Sepoy", "Lance Naik", "Naik", "Havildar", "Naib Subedar", "Subedar", "Subedar Major")
                    ),
                    subItems = listOf(
                        KnowledgeSubItem("Officer Entry", "NDA, CDS (IMA/OTA), TES (10+2), TGC, UES, AFMC."),
                        KnowledgeSubItem("Agnipath Scheme", "4-year short term service for Agniveers (General Duty, Tech, etc.)."),
                        KnowledgeSubItem("Honours", "Param Vir Chakra (PVC), Ashok Chakra (AC), Uniforms, and Medals.")
                    )
                ),
                KnowledgeCategory(
                    "5. Major Exercises",
                    "Joint and domestic training maneuvers.",
                    subItems = listOf(
                        KnowledgeSubItem("Domestic", "Op Brasstacks (Historic), Shoorveer, Rudra Akrosh, Shatrujeet."),
                        KnowledgeSubItem("International", "Yudh Abhyas (USA), Shakti (France), Nomadic Elephant (Mongolia), Ashwamedha.")
                    )
                ),
                KnowledgeCategory(
                    "6. Equipment and Modernisation",
                    "Current assets and future force structure.",
                    subItems = listOf(
                        KnowledgeSubItem("Armour & Artillery", "T-90 Bhishma, Arjun MBT, K9-Vajra, Dhanush, Pinaka MBRL."),
                        KnowledgeSubItem("Aviation", "HAL Rudra, LCH Prachand, ALH Dhruv, Apache (upcoming)."),
                        KnowledgeSubItem("Future Force", "Integrated Battle Groups (IBGs), Rudra all-arms brigades."),
                        KnowledgeSubItem("Special Units", "Bhairav Light Commando Battalions, Shaktibaan regiments, Divyastra batteries.")
                    )
                ),
                KnowledgeCategory(
                    "7. SSB Centers (Army)",
                    "Selection Centers.",
                    subItems = listOf(
                        KnowledgeSubItem("Allahabad (East)", "11, 14, 18, 19, 34 SSB."),
                        KnowledgeSubItem("Bhopal (Central)", "20, 21, 22 SSB."),
                        KnowledgeSubItem("Bangalore (South)", "17, 24 SSB."),
                        KnowledgeSubItem("Kapurthala (North)", "31, 32 SSB.")
                    )
                )
            )
            "AirForce" -> listOf(
                KnowledgeCategory(
                    "1. Mission and Identity",
                    "The core vision and symbols of the IAF.",
                    subItems = listOf(
                        KnowledgeSubItem("Motto", "Nabhah Sprusham Deeptam (Touch the Sky with Glory) - derived from Gita."),
                        KnowledgeSubItem("Primary Mission", "Defend Indian airspace and conduct aerial warfare."),
                        KnowledgeSubItem("Secondary Mission", "Humanitarian Assistance and Disaster Relief (HADR)."),
                        KnowledgeSubItem("Crest", "Himalayan Eagle with the National Emblem and Motto.")
                    )
                ),
                KnowledgeCategory(
                    "2. History of IAF",
                    "Evolution through conflicts and growth.",
                    subItems = listOf(
                        KnowledgeSubItem("Formation (1932)", "Founded on 8 Oct 1932. Early pilots: Subroto Mukerjee (Father of IAF)."),
                        KnowledgeSubItem("World War II", "IAF expanded significantly; fought in Burma Campaign."),
                        KnowledgeSubItem("Independence (1947-50)", "Division of assets; 1947 Kashmir War support."),
                        KnowledgeSubItem("Congo & Goa (1960-61)", "UN Mission in Congo; Annexation of Goa (Op Vijay)."),
                        KnowledgeSubItem("Conflicts (1962-71)", "1962 (Sino-India), 1965 (Indo-Pak), 1971 (Bangladesh Liberation)."),
                        KnowledgeSubItem("Pre-Kargil (1984-88)", "Op Meghdoot (Siachen), Op Poomalai, Op Cactus."),
                        KnowledgeSubItem("Kargil War (1999)", "Op Safed Sagar - first use of precision-guided munitions."),
                        KnowledgeSubItem("Recent Airstrikes", "2019 Balakot Airstrike and subsequent standoff."),
                        KnowledgeSubItem("Note", "Historical data curated from expert analyses of Indian defense history.")
                    )
                ),
                KnowledgeCategory(
                    "3. Organizational Structure",
                    "The administrative and operational hierarchy.",
                    subItems = listOf(
                        KnowledgeSubItem("Hierarchy", "Commands > Wings > Stations > Squadrons > Flights > Sections."),
                        KnowledgeSubItem("Commands", "5 Operational, 1 Training (Bengaluru), 1 Maintenance (Nagpur)."),
                        KnowledgeSubItem("Elite Units", "Garud Commando Force (Airfield security/CSAR)."),
                        KnowledgeSubItem("New Agencies", "Defence Space Agency (DSA) - Tri-service agency."),
                        KnowledgeSubItem("Display Teams", "Surya Kiran (Aerobatics) and Sarang (Helicopter).")
                    ),
                    selectableItems = mapOf(
                        "Western" to "New Delhi",
                        "Eastern" to "Shillong",
                        "Central" to "Prayagraj",
                        "South Western" to "Gandhinagar",
                        "Southern" to "Thiruvananthapuram"
                    )
                ),
                KnowledgeCategory(
                    "4. Personnel and Ranks",
                    "Officer and Airmen career paths.",
                    rankGroups = mapOf(
                        "Officers" to listOf("Flying Officer", "Flight Lieutenant", "Squadron Leader", "Wing Commander", "Group Captain", "Air Commodore", "Air Vice Marshal", "Air Marshal", "Air Chief Marshal", "Marshal of the Air Force"),
                        "Airmen" to listOf("Aircraftman", "Leading Aircraftman", "Corporal", "Sergeant", "Junior Warrant Officer", "Warrant Officer", "Master Warrant Officer")
                    ),
                    subItems = listOf(
                        KnowledgeSubItem("Personnel", "Honorary Officers, Non-Combatants Enrolled (NCs), Civilians."),
                        KnowledgeSubItem("Training", "AFA (Dundigal), AFTC (Jalahalli), and specialized schools.")
                    )
                ),
                KnowledgeCategory(
                    "5. Aircraft Inventory",
                    "The flying assets of the IAF.",
                    subItems = listOf(
                        KnowledgeSubItem("Combat", "Rafale, Su-30MKI, HAL Tejas, MiG-29, Mirage 2000."),
                        KnowledgeSubItem("Specialized", "AEW&C (Netra/Phalcon), Aerial Refuellers (IL-78)."),
                        KnowledgeSubItem("Transport", "C-17, C-130J, IL-76, An-32, Dornier 228."),
                        KnowledgeSubItem("Helicopters", "Prachand (LCH), Apache, Chinook, Mi-17V5, Dhruv."),
                        KnowledgeSubItem("Trainers", "PC-7, Hawk Mk.132, Kiran, HTT-40."),
                        KnowledgeSubItem("UAVs", "Heron, Searcher, Rustom (DRDO).")
                    )
                ),
                KnowledgeCategory(
                    "6. Missile Systems",
                    "Air-defense and offensive capabilities.",
                    subItems = listOf(
                        KnowledgeSubItem("Air Defence", "S-400 Triumf, Akash, MRSAM (Barak-8)."),
                        KnowledgeSubItem("Offensive", "BrahMos (Cruise), Astra (AAM), Rudram (Anti-Radiation)."),
                        KnowledgeSubItem("Anti-Tank", "SANT, Helina (for helicopters).")
                    )
                ),
                KnowledgeCategory(
                    "7. Future of IAF",
                    "Upcoming projects and modernization.",
                    subItems = listOf(
                        KnowledgeSubItem("Acquisitions", "MRFA (114 fighters), Tejas Mk1A, Tejas Mk2."),
                        KnowledgeSubItem("DRDO/HAL", "AMCA (5th Gen Stealth), TEDBF (Naval), IMRH."),
                        KnowledgeSubItem("Modernization", "Network-Centric Warfare (IACCS), Digitization."),
                        KnowledgeSubItem("Strategy", "Renaming and potential integration into Theater Commands.")
                    )
                )
            )
            "Navy" -> listOf(
                KnowledgeCategory(
                    "1. Maritime History",
                    "Evolution from ancient times to modern era.",
                    subItems = listOf(
                        KnowledgeSubItem("Early & Middle Ages", "Maritime power of Chola, Chera, Pandya, and Chhatrapati Shivaji (Father of Navy)."),
                        KnowledgeSubItem("EIC to Independence", "Establishment of Bombay Marine (1612) and Royal Indian Navy."),
                        KnowledgeSubItem("20th Century", "Post-1947 evolution; 1971 Op Trident (Missile attack on Karachi)."),
                        KnowledgeSubItem("21st Century", "Strategic shift to Blue Water Navy and Indo-Pacific security."),
                        KnowledgeSubItem("Current Role", "Protect maritime interests, HADR, and anti-piracy operations.")
                    )
                ),
                KnowledgeCategory(
                    "2. Organisation and Training",
                    "Administrative and elite structures.",
                    subItems = listOf(
                        KnowledgeSubItem("Organisation", "NHQ (New Delhi); Commands; Shore establishments."),
                        KnowledgeSubItem("Facilities", "Major bases: INS Kadamba (Karwar), INS Hansa (Goa), INS Garuda (Kochi)."),
                        KnowledgeSubItem("MARCOS", "Marine Commandos - Specialized in maritime special ops, CSAR, and sabotage."),
                        KnowledgeSubItem("Training", "Indian Naval Academy (Ezhimala), INS Shivaji, INS Chilka.")
                    ),
                    selectableItems = mapOf(
                        "Western Command" to "Mumbai",
                        "Eastern Command" to "Visakhapatnam",
                        "Southern Command" to "Kochi",
                        "ANC (Tri-Service)" to "Port Blair"
                    )
                ),
                KnowledgeCategory(
                    "3. Personnel and Ranks",
                    "Hierarchy and selection routes.",
                    rankGroups = mapOf(
                        "Officers" to listOf("Sub Lieutenant", "Lieutenant", "Lt Commander", "Commander", "Captain", "Commodore", "Rear Admiral", "Vice Admiral", "Admiral", "Admiral of the Fleet"),
                        "Sailors" to listOf("Seaman II", "Seaman I", "Leading Seaman", "Petty Officer", "Chief Petty Officer", "MCPO II", "MCPO I")
                    ),
                    subItems = listOf(
                        KnowledgeSubItem("Entry Schemes", "NDA, CDS, INET, UES, 10+2 B.Tech Entry."),
                        KnowledgeSubItem("Naval Air Arm", "Dedicated pilots and observers for aircraft carriers and reconnaissance."),
                        KnowledgeSubItem("Ensign", "Current design inspired by the royal seal of Chhatrapati Shivaji.")
                    )
                ),
                KnowledgeCategory(
                    "4. Naval Equipment",
                    "Vessels and aviation inventory.",
                    subItems = listOf(
                        KnowledgeSubItem("Ships", "Aircraft Carriers (Vikrant, Vikramaditya), Destroyers (P-15B), Frigates (P-17A), Corvettes."),
                        KnowledgeSubItem("Submarines", "Kalvari-class (Scorpene), Arihant-class (Nuclear SSBN), Chakra (SSN)."),
                        KnowledgeSubItem("Aircraft", "P-8I Poseidon, MiG-29K, MH-60R Seahawk, Sea Guardian (UAV)."),
                        KnowledgeSubItem("Satellites", "GSAT-7 (Rukmini) - India's first dedicated military satellite.")
                    )
                ),
                KnowledgeCategory(
                    "5. Activities and Weapons",
                    "Drills and offensive power.",
                    subItems = listOf(
                        KnowledgeSubItem("Exercises", "Malabar (Quad), Milan (Multi-national), Varuna (France)."),
                        KnowledgeSubItem("Weapon Systems", "BrahMos (Cruise Missile), Barak-8 (LRSAM), Varunastra (Torpedo)."),
                        KnowledgeSubItem("Electronic Warfare", "Maareecha (Torpedo Defence), Shakti (EW suite)."),
                        KnowledgeSubItem("Fleet Reviews", "Presidential Fleet Review (PFR) - ceremonial inspection of the fleet.")
                    )
                ),
                KnowledgeCategory(
                    "6. Future of Indian Navy",
                    "Upcoming projects and modernization.",
                    subItems = listOf(
                        KnowledgeSubItem("Expansion", "Planned 175-ship fleet; Project 75I (Submarines)."),
                        KnowledgeSubItem("Next Gen", "INS Vishal (planned IAC-2), Project 18 (Next-gen destroyers)."),
                        KnowledgeSubItem("Technology", "Electromagnetic Aircraft Launch System (EMALS), Unmanned platforms.")
                    )
                )
            )
            "Medical" -> listOf(
                KnowledgeCategory(
                    "1. Common Surgical Conditions",
                    "Issues often found during SSB medicals.",
                    subItems = listOf(
                        KnowledgeSubItem("DNS (Deviated Nasal Septum)", "Bent cartilage in nose. Treatment: Septoplasty (minor surgery)."),
                        KnowledgeSubItem("Ear Wax", "Blockage. TR (Temporary Rejection). Action: Use wax-dissolving drops or clinical syringing."),
                        KnowledgeSubItem("Tonsillitis", "Swelling of tonsils. Chronic cases require Tonsillectomy.")
                    )
                ),
                KnowledgeCategory(
                    "2. Orthopedic & Bone Issues",
                    "Structural alignments.",
                    subItems = listOf(
                        KnowledgeSubItem("Knock Knees (Genu Valgum)", "Knees touch while feet are apart. Remedy: Horse riding, pillow between knees while sleeping, exercises."),
                        KnowledgeSubItem("Flat Foot (Pes Planus)", "Lack of arch. PR (Permanent Rejection) usually, but mild cases may pass with arch-support insoles."),
                        KnowledgeSubItem("Carry Angle", "Angle of arm > 15-18 degrees. TR/PR depending on severity.")
                    )
                ),
                KnowledgeCategory(
                    "3. Eye and Vision Standards",
                    "Crucial for Flying/Tech branches.",
                    subItems = listOf(
                        KnowledgeSubItem("Myopia/Hypermetropia", "Standards vary by branch (e.g., 6/6 for Pilot). LASIK is permitted under specific conditions (age > 20, stable power)."),
                        KnowledgeSubItem("Color Blindness", "CP-I/II/III. Flying branch requires CP-I. PR if failing Ishihara plates.")
                    )
                ),
                KnowledgeCategory(
                    "4. Dermatological & Misc",
                    "Skin and other conditions.",
                    subItems = listOf(
                        KnowledgeSubItem("Sweaty Palms (Hyperhidrosis)", "Excessive sweating. TR/PR. Remedy: Botox or iontophoresis (in mild cases)."),
                        KnowledgeSubItem("Tattoos", "Permitted only on inner forearm or back of hand (tribal tattoos on face/body may be exempt)."),
                        KnowledgeSubItem("Underweight/Overweight", "BMI must be within 18-25 range. TR given for 42 days to achieve target weight.")
                    )
                ),
                KnowledgeCategory(
                    "5. What to do if TR/PR is given?",
                    "The Appeal Process.",
                    subItems = listOf(
                        KnowledgeSubItem("Temporary Rejection (TR)", "You get 42 days to rectify the issue and report to the designated AMB (Appeal Medical Board)."),
                        KnowledgeSubItem("Permanent Rejection (PR)", "You can apply for a Review Medical Board (RMB) within 24 hours if you feel the diagnosis is incorrect.")
                    )
                )
            )
            "General" -> listOf(
                KnowledgeCategory(
                    "1. Current Affairs & IR",
                    "National and international issues.",
                    subItems = listOf(
                        KnowledgeSubItem("International Relations", "Indo-China, Indo-Pak (4 key issues), South China Sea."),
                        KnowledgeSubItem("National Issues", "Kaveri water dispute, Women’s empowerment policies."),
                        KnowledgeSubItem("Projects", "Sagarmala, Bharatmala, Kaladan projects.")
                    )
                ),
                KnowledgeCategory(
                    "2. Geography & Civics",
                    "Physical and political landscape.",
                    subItems = listOf(
                        KnowledgeSubItem("Borders", "Uttarakhand, Karnataka, China, Pakistan borders."),
                        KnowledgeSubItem("Coastline", "List of Indian coastline states."),
                        KnowledgeSubItem("North-East", "Seven Sisters and their Capitals."),
                        KnowledgeSubItem("Leaders", "Chief Ministers, Governors, PMs, and Presidents.")
                    )
                ),
                KnowledgeCategory(
                    "3. Basic Science",
                    "Physics and Chemistry fundamentals.",
                    subItems = listOf(
                        KnowledgeSubItem("Physics Laws", "Ohm’s, Faraday’s, Newton’s, Archimedes’, Bernoulli’s."),
                        KnowledgeSubItem("Chemistry", "Chemical names of Gold (Au) and Silver (Ag).")
                    )
                ),
                KnowledgeCategory(
                    "4. Mechanical & Tech",
                    "Engineering basics.",
                    subItems = listOf(
                        KnowledgeSubItem("Engines", "Difference between 2-stroke and 4-stroke engines."),
                        KnowledgeSubItem("Power", "Difference between Motors and Generators."),
                        KnowledgeSubItem("Appliance", "Working principle of Refrigerators."),
                        KnowledgeSubItem("Radar", "Radio Detection and Ranging.")
                    )
                )
            )
            "SWOT" -> listOf(
                KnowledgeCategory(
                    "1. Identifying Strengths",
                    "Positive traits that align with OLQs.",
                    subItems = listOf(
                        KnowledgeSubItem("Leadership", "Ability to lead a team towards a common goal."),
                        KnowledgeSubItem("Integrity", "Being honest and having strong moral principles."),
                        KnowledgeSubItem("Determination", "The firmness of purpose; resolute."),
                        KnowledgeSubItem("Adaptability", "Quality of being able to adjust to new conditions."),
                        KnowledgeSubItem("Effective Communication", "The ability to convey information effectively and efficiently.")
                    )
                ),
                KnowledgeCategory(
                    "2. Understanding Weaknesses",
                    "Areas of improvement that you should be aware of.",
                    subItems = listOf(
                        KnowledgeSubItem("Public Speaking", "Feeling nervous while speaking in front of a large crowd."),
                        KnowledgeSubItem("Time Management", "Difficulty in prioritizing tasks and meeting deadlines."),
                        KnowledgeSubItem("Perfectionism", "Spending too much time on a single task to make it perfect."),
                        KnowledgeSubItem("Delegation", "Finding it hard to trust others with important tasks."),
                        KnowledgeSubItem("Impatience", "A tendency to be annoyed by delay or opposition.")
                    )
                ),
                KnowledgeCategory(
                    "3. Improvement Plan",
                    "Actionable steps to enhance your personality.",
                    subItems = listOf(
                        KnowledgeSubItem("Self-Reflection", "Regularly assess your actions and their outcomes."),
                        KnowledgeSubItem("Skill Building", "Take courses or read books to improve specific skills like communication or leadership."),
                        KnowledgeSubItem("Seeking Feedback", "Ask for honest feedback from friends, family, or mentors."),
                        KnowledgeSubItem("Setting Goals", "Define clear, achievable goals for your personal growth."),
                        KnowledgeSubItem("Physical Fitness", "Maintain a regular exercise routine to stay physically and mentally fit.")
                    )
                )
            )
            "INTERVIEW_GUIDE" -> listOf(
                KnowledgeCategory(
                    "1. Interview Process",
                    "Overview of the Personal Interview.",
                    subItems = listOf(
                        KnowledgeSubItem("Duration", "Usually lasts 45 to 90 minutes depending on the candidate's background."),
                        KnowledgeSubItem("The IO", "Conducted by the President or Deputy President of the Board in a one-on-one setting."),
                        KnowledgeSubItem("Atmosphere", "Conversational and professional. The IO tries to put you at ease to see the real 'you'."),
                        KnowledgeSubItem("Objective", "To assess 15 Officer Like Qualities (OLQs) and check overall suitability.")
                    )
                ),
                KnowledgeCategory(
                    "2. Preparation",
                    "How to gear up effectively.",
                    subItems = listOf(
                        KnowledgeSubItem("PIQ Form", "Your 'Bible'. Know every word you wrote in it (Hobbies, Achievements, Sports)."),
                        KnowledgeSubItem("Self-Analysis", "Understand your life story, turning points, and influences."),
                        KnowledgeSubItem("GK & Current Affairs", "Read newspapers (The Hindu/Indian Express) daily for at least 6 months prior."),
                        KnowledgeSubItem("Service Knowledge", "Know about the service you applied for (Ranks, Aircraft, Recent Defense Deals)."),
                        KnowledgeSubItem("Mirror Practice", "Speak in front of a mirror to fix facial expressions and tone.")
                    )
                ),
                KnowledgeCategory(
                    "3. Sitting Posture & Body Language",
                    "Non-verbal signals are vital.",
                    subItems = listOf(
                        KnowledgeSubItem("The Entry", "Smart walk, stand near the chair, greet with a smile, sit only when asked."),
                        KnowledgeSubItem("Posture", "Sit straight, back against the chair. Avoid leaning too far back or slouching."),
                        KnowledgeSubItem("Hands", "Rest hands comfortably on your lap. Do not cross arms or fidget with fingers."),
                        KnowledgeSubItem("Eye Contact", "Steady, natural eye contact. If there are two officers, look at the one who asked the question."),
                        KnowledgeSubItem("Voice", "Maintain a clear, moderate volume. Don't whisper or shout.")
                    )
                ),
                KnowledgeCategory(
                    "4. Family Related Questions",
                    "IO wants to see your social bonds.",
                    subItems = listOf(
                        KnowledgeSubItem("Structure", "Details about parents and siblings - occupation, income, and education."),
                        KnowledgeSubItem("Dynamics", "Who are you closest to? Why? Who do you go to for advice?"),
                        KnowledgeSubItem("Comparison", "How are you different from your brother/sister?"),
                        KnowledgeSubItem("Contribution", "How do you help your parents in daily chores or financial management?"),
                        KnowledgeSubItem("Example", "'I am closest to my mother because she is my confidant, but I look up to my father for his resilience.'")
                    )
                ),
                KnowledgeCategory(
                    "5. Working Professionals",
                    "For candidates with work experience.",
                    subItems = listOf(
                        KnowledgeSubItem("Job Profile", "Your exact role and responsibilities. Challenges you faced."),
                        KnowledgeSubItem("The Switch", "Why leave a stable job for the Armed Forces? (Avoid 'Adventure' as the only reason)."),
                        KnowledgeSubItem("Salary", "How do you spend your salary? Do you support your family? (Shows responsibility)."),
                        KnowledgeSubItem("Work Culture", "What have you learned about teamwork and deadlines from your job?")
                    )
                ),
                KnowledgeCategory(
                    "6. Place of Residence",
                    "Your awareness of your surroundings.",
                    subItems = listOf(
                        KnowledgeSubItem("Details", "History, culture, famous landmarks, and major industries of your city."),
                        KnowledgeSubItem("Demography", "Population, literacy rate, and sex ratio (approximate)."),
                        KnowledgeSubItem("Local Issues", "Major problems (pollution, traffic, water) and how you would fix them."),
                        KnowledgeSubItem("Example", "If from Delhi: 'I'd focus on enhancing the public transport network to reduce AQI levels.'")
                    )
                ),
                KnowledgeCategory(
                    "7. PI Do's and Don'ts",
                    "Quick behavioral rules.",
                    subItems = listOf(
                        KnowledgeSubItem("Do's", "Be honest, dress sharp, listen carefully, admit when you don't know, stay cheerful."),
                        KnowledgeSubItem("Don'ts", "Don't bluff, don't use slang, don't criticize teachers/bosses, don't interrupt the IO.")
                    )
                ),
                KnowledgeCategory(
                    "8. Education Related Questions",
                    "Your academic performance and choices.",
                    subItems = listOf(
                        KnowledgeSubItem("Academics", "Favorite subjects and why? Reasons for choosing your stream."),
                        KnowledgeSubItem("Variations", "Reason for drop in marks from 10th to 12th or Graduation."),
                        KnowledgeSubItem("Teachers", "Your favorite and least favorite teacher and reasons for both."),
                        KnowledgeSubItem("Campus Life", "Your involvement in clubs, fests, and overall college life.")
                    )
                ),
                KnowledgeCategory(
                    "9. Introductory Questions",
                    "The Rapid Fire round.",
                    subItems = listOf(
                        KnowledgeSubItem("Chronology", "10th -> 12th -> Graduation -> Family -> Hobbies. IO asks 5-10 questions in one go."),
                        KnowledgeSubItem("Retention", "Remember the sequence. If you miss a question, ask politely at the end."),
                        KnowledgeSubItem("Confidence", "Don't rush. Take a breath and answer point by point.")
                    )
                ),
                KnowledgeCategory(
                    "10. Hobbies and Interests",
                    "Showing your personality beyond work.",
                    subItems = listOf(
                        KnowledgeSubItem("Definitions", "Hobby is active (Painting, Running). Interest is passive (Geopolitics, Reading)."),
                        KnowledgeSubItem("Depth", "Know technical details. If Painting: 'What are the types of brushes/colors you use?'"),
                        KnowledgeSubItem("Utility", "How does your hobby help you relax or grow?")
                    )
                ),
                KnowledgeCategory(
                    "11. Sports",
                    "Team spirit and fitness.",
                    subItems = listOf(
                        KnowledgeSubItem("Technicalities", "Rules of the game, dimensions of the field/court, famous players."),
                        KnowledgeSubItem("Participation", "Level played (School/National). Achievements if any."),
                        KnowledgeSubItem("Leadership", "If you were captain, how did you handle a losing streak?"),
                        KnowledgeSubItem("Example", "For Cricket: Know the pitch length (22 yards) and recent ICC winners.")
                    )
                ),
                KnowledgeCategory(
                    "12. Co-curricular Activities",
                    "Broadening your horizons.",
                    subItems = listOf(
                        KnowledgeSubItem("Events", "Debates, Dramatics, NCC, NSS, Organizing festivals."),
                        KnowledgeSubItem("Learning", "Confidence building, social skills, and public speaking."),
                        KnowledgeSubItem("Social Impact", "Any community service or NGO work you've done.")
                    )
                ),
                KnowledgeCategory(
                    "13. Friends Related",
                    "Your social circle and influence.",
                    subItems = listOf(
                        KnowledgeSubItem("Quality", "How many close friends? Why are they close?"),
                        KnowledgeSubItem("Feedback", "What is one thing they like about you? One thing they hate?"),
                        KnowledgeSubItem("Influence", "How do you handle a friend who is taking a wrong path?")
                    )
                ),
                KnowledgeCategory(
                    "14. Emotional Clarity",
                    "Self-regulation and maturity.",
                    subItems = listOf(
                        KnowledgeSubItem("Anger", "What makes you angry? How do you react? (Example: 'I get upset with laziness but I try to motivate them')."),
                        KnowledgeSubItem("Fear", "One thing you are afraid of and how you face it."),
                        KnowledgeSubItem("Happiness", "What was the happiest moment of your life so far?")
                    )
                ),
                KnowledgeCategory(
                    "15. Character & Responsibility (SRTs)",
                    "Handling real-life situations.",
                    subItems = listOf(
                        KnowledgeSubItem("Situation 1", "You see an accident while going for an exam. What do you do? (Help + Exam management)."),
                        KnowledgeSubItem("Situation 2", "You find a purse full of cash in a train. (Check for ID -> Station Master/Police)."),
                        KnowledgeSubItem("Logic", "Always show initiative, social responsibility, and honesty.")
                    )
                ),
                KnowledgeCategory(
                    "16. Leadership & Organizing Ability",
                    "Managing resources and people.",
                    subItems = listOf(
                        KnowledgeSubItem("Experience", "Describe a time you organized a trip or a college event."),
                        KnowledgeSubItem("Challenges", "How did you handle lack of funds or team members not showing up?"),
                        KnowledgeSubItem("Decision Making", "Tell me about a difficult decision you took for the team.")
                    )
                ),
                KnowledgeCategory(
                    "17. Reasoning & Thinking Ability",
                    "Analytical and logical approach.",
                    subItems = listOf(
                        KnowledgeSubItem("Why SSB?", "3 logical reasons why you are better than others."),
                        KnowledgeSubItem("Planning", "How would you plan a trekking trip for 10 people with a tight budget?"),
                        KnowledgeSubItem("Optimization", "Finding the fastest/best way to solve a given logistical problem.")
                    )
                ),
                KnowledgeCategory(
                    "18. Self Awareness",
                    "Knowing your own mind.",
                    subItems = listOf(
                        KnowledgeSubItem("Strengths", "Connect them to OLQs (Resilience, Adaptability)."),
                        KnowledgeSubItem("Weaknesses", "Must be genuine but workable. (Example: 'I am working on my English vocabulary')."),
                        KnowledgeSubItem("Growth", "How have you improved as a person in the last 2 years?")
                    )
                ),
                KnowledgeCategory(
                    "19. Position of Responsibility",
                    "Authority and Accountability.",
                    subItems = listOf(
                        KnowledgeSubItem("Roles", "School Captain, Placement Cell Head, Sports Secretary."),
                        KnowledgeSubItem("Achievements", "One significant change you brought while in that position."),
                        KnowledgeSubItem("Duty", "What was your daily routine in that role?")
                    )
                ),
                KnowledgeCategory(
                    "20. Estimation Questions",
                    "Checking numerical logic and calm.",
                    subItems = listOf(
                        KnowledgeSubItem("Example 1", "What is the weight of this room? (Explain volume and density of air/concrete)."),
                        KnowledgeSubItem("Example 2", "How many liters of fuel are consumed in your city daily?"),
                        KnowledgeSubItem("Tip", "IO looks for the process, not just the final number. Don't say 'I don't know'.")
                    )
                ),
                KnowledgeCategory(
                    "21. Brain Teasers",
                    "Lateral thinking and stress test.",
                    subItems = listOf(
                        KnowledgeSubItem("Example 1", "Why are manhole covers round? (They don't fall through the hole)."),
                        KnowledgeSubItem("Example 2", "A farmer has 17 sheep, all but 9 die. How many are left? (9)."),
                        KnowledgeSubItem("Reaction", "Keep a cool head and think logically.")
                    )
                ),
                KnowledgeCategory(
                    "22. Interest in Armed Forces",
                    "Your motivation and research.",
                    subItems = listOf(
                        KnowledgeSubItem("Motivation", "Why join the forces? Why not Civil Services?"),
                        KnowledgeSubItem("Research", "Commands of the service, current weapon systems, recent operations."),
                        KnowledgeSubItem("Entry", "Why did you choose this specific entry (NDA/CDS/TGC)?")
                    )
                ),
                KnowledgeCategory(
                    "23. General Knowledge",
                    "Basic awareness of the world.",
                    subItems = listOf(
                        KnowledgeSubItem("Indian Polity", "Constitution, Preamble, Fundamental Rights."),
                        KnowledgeSubItem("Geography", "Boundaries, Rivers, Major Peaks, Neighboring countries."),
                        KnowledgeSubItem("International Organizations", "UN, BRICS, G20, NATO - their roles and recent summits.")
                    )
                ),
                KnowledgeCategory(
                    "24. Current Affairs",
                    "What's happening now.",
                    subItems = listOf(
                        KnowledgeSubItem("National", "New Government Bills, Economic policies, ISRO missions."),
                        KnowledgeSubItem("International", "Major global conflicts (Russia-Ukraine, Israel-Palestine), Climate change summits."),
                        KnowledgeSubItem("Defense", "Recent inductions (Rafale, Vikrant, S-400) and joint exercises.")
                    )
                ),
                KnowledgeCategory(
                    "25. Repeaters / Previous Attempts",
                    "Introspection and resilience.",
                    subItems = listOf(
                        KnowledgeSubItem("Failure Analysis", "What was the reason for your previous rejection according to you?"),
                        KnowledgeSubItem("Improvements", "What changes did you make in your personality since your last attempt?"),
                        KnowledgeSubItem("Preparation", "How is your preparation different this time?")
                    )
                ),
                KnowledgeCategory(
                    "26. Future Plans",
                    "Your vision and pragmatism.",
                    subItems = listOf(
                        KnowledgeSubItem("Plan B", "What if you don't get recommended? (Shows maturity and resilience)."),
                        KnowledgeSubItem("Long-term Goal", "Where do you see yourself 10-15 years from now in the forces?")
                    )
                ),
                KnowledgeCategory(
                    "27. Basic Technical Knowledge",
                    "For Engineering/Technical entries.",
                    subItems = listOf(
                        KnowledgeSubItem("Fundamentals", "Core principles of your branch (Bernoulli's, Ohm's Law, etc.)."),
                        KnowledgeSubItem("Application", "How will you use your engineering knowledge in the field?"),
                        KnowledgeSubItem("Projects", "Details of your final year project and its utility.")
                    )
                ),
                KnowledgeCategory(
                    "28. Code of Conduct",
                    "Etiquette and manners.",
                    subItems = listOf(
                        KnowledgeSubItem("Dress Code", "Ironed formal shirt, trousers, tie, polished shoes. Well-groomed hair."),
                        KnowledgeSubItem("Behavior", "Polite, use of 'Sir', no over-smartness, no excessive hand gestures."),
                        KnowledgeSubItem("Entry/Exit", "Ask permission to enter, thank the officer while leaving, walk out with confidence.")
                    )
                )
            )
            else -> emptyList()
        }
    }
}

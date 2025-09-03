package com.example.calculator.models

data class Constant(
    val symbol: String,
    val name: String,
    val value: Double
)

val constantsMathematics = listOf(
    Constant("e", "Euler's number", 2.7182818284590452354),
    Constant("π", "Pi", 3.14159265358979323846),
    Constant("φ", "Golden ratio", 1.6180339887498948482),
    Constant("γ", "Euler–Mascheroni constant", 0.5772156649015328606),
    Constant("μ", "Connective constant for the hexagonal lattice", 1.84775906502257351225),
    Constant("λ", "Lemniscate constant", 2.62205755429211981046),
    Constant("E", "Erdős–Borwein constant", 1.60669515241529176378),
    Constant("K", "Catalan's constant", 0.91596559417721901505),
    Constant("ζ(3)", "Apéry's constant (zeta(3))", 1.2020569031595942854),
    Constant("Ω", "Omega constant (solution of W = 1)", 0.567143290409784),
    Constant("δ_S", "Sierpiński's constant", 2.58498175957925321706),
    Constant("C_2", "Twin prime constant", 0.6601618158468695739),
    Constant("C_4", "Quadratic reciprocity constant", 0.7642236535892206629),
    Constant("Li₂(1)", "Dilogarithm at 1 (ζ(2))", 1.6449340668482264365), // π²/6
    Constant("Li₄(1)", "Tetralogarithm at 1 (ζ(4))", 1.0823232337111381915), // π⁴/90
    Constant("M", "Meissel–Mertens constant", 0.26149721284764278375),
    Constant("B₂", "Bernoulli constant B₂", 1.6449340668482264365), // same as ζ(2)
    Constant("B₄", "Bernoulli constant B₄", -1.0823232337111381915)
)

val constantsScience = listOf(
    Constant("g", "Gravitational acceleration (m/s²)", 9.80665),
    Constant("c", "Speed of light in vacuum (m/s)", 299792458.0),
    Constant("h", "Planck constant (J·s)", 6.62607015e-34),
    Constant("ħ", "Reduced Planck constant (ħ = h/2π)", 1.054571817e-34),
    Constant("k_B", "Boltzmann constant (J/K)", 1.380649e-23),
    Constant("G", "Newton's gravitational constant (m^3·kg⁻¹·s⁻²)", 6.67430e-11),
    Constant("N_A", "Avogadro constant (mol⁻¹)", 6.02214076e23),
    Constant("R", "Molar gas constant (J/(mol·K))", 8.314462618),
    Constant("e", "Elementary charge (C)", 1.602176634e-19),
    Constant("α", "Fine-structure constant", 7.2973525693e-3), // ~ 1/137
    Constant("ε₀", "Vacuum permittivity (F/m)", 8.8541878128e-12),
    Constant("μ₀", "Vacuum permeability (N/A²)", 1.25663706212e-6),
    Constant("m_e", "Electron mass (kg)", 9.1093837015e-31),
    Constant("m_p", "Proton mass (kg)", 1.67262192369e-27),
    Constant("m_n", "Neutron mass (kg)", 1.67492749804e-27),
    Constant("σ", "Stefan–Boltzmann constant (W·m⁻²·K⁻⁴)", 5.670374419e-8),
    Constant("λ_C", "Compton wavelength of the electron (m)", 2.426310238e-12),
    Constant("F", "Faraday constant (C/mol)", 96485.33212),
    Constant("μ_B", "Bohr magneton (J/T)", 9.2740100783e-24),
    Constant("μ_N", "Nuclear magneton (J/T)", 5.0507837461e-27),
    Constant("Φ₀", "Magnetic flux quantum (Wb)", 2.067833848e-15),
    Constant("R_K", "von Klitzing constant (Ω)", 25812.80745),
    Constant("K_J", "Josephson constant (Hz/V)", 483597.8484e9),
    Constant("τ_n", "Mean lifetime of a free neutron (s)", 880.2),
    Constant("T_CMB", "Cosmic microwave background temperature (K)", 2.72548)
)
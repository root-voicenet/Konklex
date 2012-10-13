package org.apollo.util;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;

/**
 * Gets some system information.
 * @author Steve
 */
public final class SystemUtil {

	/**
	 * The sigar api.
	 */
	private static Sigar sigar = new Sigar();

	/**
	 * Get the system load average.
	 * @return The system load average.
	 */
	public static int getCpuUsage() {
		int cpu = 0;
		try {
			final ProcCpu procCPU = sigar.getProcCpu(sigar.getPid());
			cpu = (int) Double.parseDouble(CpuPerc.format(procCPU.getPercent()).replace("%", ""));
		} catch (final Exception e) {
		}
		return cpu;
	}

	/**
	 * Gets the process id.
	 * @return pid The process id.
	 */
	public static long getProcessId() {
		return sigar.getPid();
	}

	/**
	 * Get the system ram usage in MB.
	 * @return The ram usage in MB.
	 */
	public static int getRamUsage() {
		int ram = 0;
		try {
			final ProcMem procMem = sigar.getProcMem(sigar.getPid());
			ram = (int) (procMem.getResident() / 1024) / 1024;
		} catch (final Exception e) {
		}
		return ram;
	}
}
